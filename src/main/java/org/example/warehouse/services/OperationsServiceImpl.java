package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.config.AppConstants;
import org.example.warehouse.dao.OperationInfoDAO;
import org.example.warehouse.dao.OperationTypeDAO;
import org.example.warehouse.dao.OperationsDAO;
import org.example.warehouse.dao.ProductsDAO;
import org.example.warehouse.dto.*;
import org.example.warehouse.entities.OperationTypeEntity;
import org.example.warehouse.entities.OperationsEntity;
import org.example.warehouse.entities.OperationsEntityFull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OperationsServiceImpl implements OperationsService {
    private final OperationsDAO operationsDAO;
    private final OperationTypeDAO operationTypeDAO;
    private final OperationInfoDAO operationInfoDAO;
    private final ProductsDAO productsDAO;

    @Override
    @Transactional
    public boolean createOperation(OperationsDto operationsDto) {
        if (operationsDto == null) {
            return false;
        }
        //Получение типа операции из БД
        OperationTypeEntity typeEntity = operationTypeDAO
                .getOperationTypeByName(operationsDto.getTypeName());
        if (typeEntity == null) {
            System.out.println(operationsDto);
            return false;
        }
        //Сохранение пустой операции в БД для получения ее id
        operationsDAO.createOperation(typeEntity.getTypeId(), operationsDto.getInvoiceNumber());
        OperationsEntity operationsEntity = operationsDAO
                .getOperationByInvoice(operationsDto.getInvoiceNumber());
        if (operationsEntity == null) {
            return false;
        }
        //Определение какой тип операции
        int newProductAmount;
        boolean result = false;
        if (AppConstants.RECEPTION_TYPE.equals(typeEntity.getTypeName())) {
            for (ProductOrderDto order : operationsDto.getProductList()) {
                //Изменение количества товара
                newProductAmount = order.getCurrentProdAmount() + order.getOperationProdAmount();
                productsDAO.updateProductAmountById(order.getProductId(), newProductAmount);

                result = operationInfoDAO.createOperationInfo(
                        operationsEntity.getOperationId()
                        , order.getProductId()
                        , order.getOperationProdAmount());
            }
        } else {
            for (ProductOrderDto order : operationsDto.getProductList()) {
                //Изменение количества товара
                newProductAmount = order.getCurrentProdAmount() - order.getOperationProdAmount();
                productsDAO.updateProductAmountById(order.getProductId(), newProductAmount);

                result = operationInfoDAO.createOperationInfo(
                        operationsEntity.getOperationId()
                        , order.getProductId()
                        , order.getOperationProdAmount());
            }
        }
        return result;
    }

    @Override
    public OperationsDtoRes getOperationById(OperationsDto operationsDto) {
        if (operationsDto == null) {
            return null;
        }
        OperationsEntity entity = operationsDAO.getOperationById(operationsDto.getOperationId());
        OperationsDtoRes operationsDtoRes = null;
        if (entity != null) {
            operationsDtoRes = new OperationsDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setOperationId(entity.getOperationId())
                    .setOperationDate(entity.getOperationDate())
                    .setTypeId(entity.getTypeId())
                    .setInvoiceNumber(entity.getInvoiceNumber());
        }
        return operationsDtoRes;
    }

    @Override
    public OperationsDtoRes getOperationByInvoice(OperationsDto operationsDto) {
        if (operationsDto == null) {
            return null;
        }
        OperationsEntity entity = operationsDAO.getOperationByInvoice(operationsDto.getInvoiceNumber());
        OperationsDtoRes operationsDtoRes = null;
        if (entity != null) {
            operationsDtoRes = new OperationsDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setOperationId(entity.getOperationId())
                    .setOperationDate(entity.getOperationDate())
                    .setTypeId(entity.getTypeId())
                    .setInvoiceNumber(entity.getInvoiceNumber());
        }
        return operationsDtoRes;
    }

    @Override
    public List<OperationsFullDtoRes> getOperationByInvoiceFull(OperationsDto operationsDto) {
        List<OperationsFullDtoRes> resultList = new ArrayList<>();
        if (operationsDto != null) {
            List<OperationsEntityFull> entityList = operationsDAO
                    .getOperationByInvoiceFull(operationsDto.getInvoiceNumber());

            for (OperationsEntityFull entity : entityList) {
                OperationsFullDtoRes operation = new OperationsFullDtoRes()
                        .setRowNumber(entity.getRowNumber())
                        .setOperationId(entity.getOperationId())
                        .setOperationDate(entity.getOperationDate())
                        .setTypeName(entity.getTypeName())
                        .setInvoiceNumber(entity.getInvoiceNumber())
                        .setProductId(entity.getProductId())
                        .setProductName(entity.getProductName())
                        .setProductAmount(entity.getProductAmount())
                        .setProducerName(entity.getProducerName())
                        .setCountryName(entity.getCountryName());

                resultList.add(operation);
            }
        }
        return resultList;
    }

    @Override
    public OperationReportDtoRes getOperationReportByDay() {
        OperationReportDtoRes result = new OperationReportDtoRes();
        List<OperationReportDtoRes> reportDtoResList = new ArrayList<>();

        List<OperationsEntityFull> entityList = operationsDAO
                .getAllOperationByPeriodFull(LocalDate.now(), LocalDate.now());

        Set<String> invoices = new HashSet<>();
        BigDecimal totalPrice = new BigDecimal(0);
        int totalAmount = 0;
        List<OperationsEntityFull> receptionList = new ArrayList<>();
        List<OperationsEntityFull> shipmentList = new ArrayList<>();
        List<OperationsEntityFull> writeoffList = new ArrayList<>();

        for (OperationsEntityFull entity : entityList) {
            invoices.add(entity.getInvoiceNumber());
            totalAmount = totalAmount + entity.getProductAmount();
            totalPrice = totalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));

            if (AppConstants.RECEPTION_TYPE.equals(entity.getTypeName())) {
                receptionList.add(entity);
            } else if (AppConstants.SHIPMENT_TYPE.equals(entity.getTypeName())) {
                shipmentList.add(entity);
            } else {
                writeoffList.add(entity);
            }
        }

        result.setOperationAmount(invoices.size());
        result.setProductAmount(totalAmount);
        result.setTotalCost(totalPrice);

        Set<String> receptionInvoices = new HashSet<>();
        BigDecimal receptionTotalPrice = new BigDecimal(0);
        int receptionTotalAmount = 0;

        for (OperationsEntityFull entity : receptionList) {
            receptionInvoices.add(entity.getInvoiceNumber());
            receptionTotalAmount = receptionTotalAmount + entity.getProductAmount();
            receptionTotalPrice = receptionTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes receptionType = new OperationReportDtoRes()
                .setTypeName(AppConstants.RECEPTION_TYPE)
                .setOperationAmount(receptionInvoices.size())
                .setProductAmount(receptionTotalAmount)
                .setTotalCost(receptionTotalPrice);


        Set<String> shipmentInvoices = new HashSet<>();
        BigDecimal shipmentTotalPrice = new BigDecimal(0);
        int shipmentTotalAmount = 0;

        for (OperationsEntityFull entity : shipmentList) {
            shipmentInvoices.add(entity.getInvoiceNumber());
            shipmentTotalAmount = shipmentTotalAmount + entity.getProductAmount();
            shipmentTotalPrice = shipmentTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes shipmentType = new OperationReportDtoRes()
                .setTypeName(AppConstants.SHIPMENT_TYPE)
                .setOperationAmount(shipmentInvoices.size())
                .setProductAmount(shipmentTotalAmount)
                .setTotalCost(shipmentTotalPrice);


        Set<String> writeoffInvoices = new HashSet<>();
        BigDecimal writeoffTotalPrice = new BigDecimal(0);
        int writeoffTotalAmount = 0;

        for (OperationsEntityFull entity : writeoffList) {
            writeoffInvoices.add(entity.getInvoiceNumber());
            writeoffTotalAmount = writeoffTotalAmount + entity.getProductAmount();
            writeoffTotalPrice = writeoffTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes writeoffType = new OperationReportDtoRes()
                .setTypeName(AppConstants.WRITE_OFF_TYPE)
                .setOperationAmount(writeoffInvoices.size())
                .setProductAmount(writeoffTotalAmount)
                .setTotalCost(writeoffTotalPrice);

        reportDtoResList.add(receptionType);
        reportDtoResList.add(shipmentType);
        reportDtoResList.add(writeoffType);

        result.setReportDtoResList(reportDtoResList);

        return result;
    }


    @Override
    public OperationReportDtoRes getOperationReportByPeriod(OperationsDto operationsDto) {
        OperationReportDtoRes result = new OperationReportDtoRes();
        List<OperationReportDtoRes> reportDtoResList = new ArrayList<>();

        List<OperationsEntityFull> entityList = operationsDAO
                .getAllOperationByPeriodFull(operationsDto.getStart(), operationsDto.getEnd());

        Set<String> invoices = new HashSet<>();
        BigDecimal totalPrice = new BigDecimal(0);
        int totalAmount = 0;
        List<OperationsEntityFull> receptionList = new ArrayList<>();
        List<OperationsEntityFull> shipmentList = new ArrayList<>();
        List<OperationsEntityFull> writeoffList = new ArrayList<>();

        for (OperationsEntityFull entity : entityList) {
            invoices.add(entity.getInvoiceNumber());
            totalAmount = totalAmount + entity.getProductAmount();
            totalPrice = totalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));

            if (AppConstants.RECEPTION_TYPE.equals(entity.getTypeName())) {
                receptionList.add(entity);
            } else if (AppConstants.SHIPMENT_TYPE.equals(entity.getTypeName())) {
                shipmentList.add(entity);
            } else {
                writeoffList.add(entity);
            }
        }

        result.setOperationAmount(invoices.size());
        result.setProductAmount(totalAmount);
        result.setTotalCost(totalPrice);

        Set<String> receptionInvoices = new HashSet<>();
        BigDecimal receptionTotalPrice = new BigDecimal(0);
        int receptionTotalAmount = 0;

        for (OperationsEntityFull entity : receptionList) {
            receptionInvoices.add(entity.getInvoiceNumber());
            receptionTotalAmount = receptionTotalAmount + entity.getProductAmount();
            receptionTotalPrice = receptionTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes receptionType = new OperationReportDtoRes()
                .setTypeName(AppConstants.RECEPTION_TYPE)
                .setOperationAmount(receptionInvoices.size())
                .setProductAmount(receptionTotalAmount)
                .setTotalCost(receptionTotalPrice);


        Set<String> shipmentInvoices = new HashSet<>();
        BigDecimal shipmentTotalPrice = new BigDecimal(0);
        int shipmentTotalAmount = 0;

        for (OperationsEntityFull entity : shipmentList) {
            shipmentInvoices.add(entity.getInvoiceNumber());
            shipmentTotalAmount = shipmentTotalAmount + entity.getProductAmount();
            shipmentTotalPrice = shipmentTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes shipmentType = new OperationReportDtoRes()
                .setTypeName(AppConstants.SHIPMENT_TYPE)
                .setOperationAmount(shipmentInvoices.size())
                .setProductAmount(shipmentTotalAmount)
                .setTotalCost(shipmentTotalPrice);


        Set<String> writeoffInvoices = new HashSet<>();
        BigDecimal writeoffTotalPrice = new BigDecimal(0);
        int writeoffTotalAmount = 0;

        for (OperationsEntityFull entity : writeoffList) {
            writeoffInvoices.add(entity.getInvoiceNumber());
            writeoffTotalAmount = writeoffTotalAmount + entity.getProductAmount();
            writeoffTotalPrice = writeoffTotalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        OperationReportDtoRes writeoffType = new OperationReportDtoRes()
                .setTypeName(AppConstants.WRITE_OFF_TYPE)
                .setOperationAmount(writeoffInvoices.size())
                .setProductAmount(writeoffTotalAmount)
                .setTotalCost(writeoffTotalPrice);

        reportDtoResList.add(receptionType);
        reportDtoResList.add(shipmentType);
        reportDtoResList.add(writeoffType);

        result.setReportDtoResList(reportDtoResList);

        return result;
    }

    @Override
    public OperationReportDtoRes getOperationReportByDayAndTypeFull(OperationsDto operationsDto) {
        OperationReportDtoRes result;
        List<OperationReportDtoRes> reportDtoResList = new ArrayList<>();

        List<OperationsEntityFull> entityList = operationsDAO
                .getAllOperationByPeriodAndTypeFull(LocalDate.now(), LocalDate.now()
                        , operationsDto.getTypeName());

        Set<String> invoices = new HashSet<>();
        BigDecimal totalPrice = new BigDecimal(0);
        int totalAmount = 0;
        List<OperationsEntityFull> operationList = new ArrayList<>();

        for (OperationsEntityFull entity : entityList) {
            if (operationsDto.getTypeName().equals(entity.getTypeName())) {
                operationList.add(entity);
            }
        }

        for (OperationsEntityFull entity : operationList) {
            invoices.add(entity.getInvoiceNumber());
            totalAmount = totalAmount + entity.getProductAmount();
            totalPrice = totalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        result = new OperationReportDtoRes()
                .setTypeName(operationsDto.getTypeName())
                .setOperationAmount(invoices.size())
                .setProductAmount(totalAmount)
                .setTotalCost(totalPrice);

        result.setReportDtoResList(reportDtoResList);

        return result;
    }

    @Override
    public OperationReportDtoRes getOperationReportByPeriodAndTypeFull(OperationsDto operationsDto) {
        OperationReportDtoRes result;
        List<OperationReportDtoRes> reportDtoResList = new ArrayList<>();

        List<OperationsEntityFull> entityList = operationsDAO
                .getAllOperationByPeriodAndTypeFull(operationsDto.getStart(), operationsDto.getEnd()
                        , operationsDto.getTypeName());

        Set<String> invoices = new HashSet<>();
        BigDecimal totalPrice = new BigDecimal(0);
        int totalAmount = 0;
        List<OperationsEntityFull> operationList = new ArrayList<>();

        for (OperationsEntityFull entity : entityList) {
            if (operationsDto.getTypeName().equals(entity.getTypeName())) {
                operationList.add(entity);
            }
        }

        for (OperationsEntityFull entity : operationList) {
            invoices.add(entity.getInvoiceNumber());
            totalAmount = totalAmount + entity.getProductAmount();
            totalPrice = totalPrice.add(new BigDecimal(
                    entity.getProductAmount()).multiply(entity.getPrice()));
        }

        result = new OperationReportDtoRes()
                .setTypeName(operationsDto.getTypeName())
                .setOperationAmount(invoices.size())
                .setProductAmount(totalAmount)
                .setTotalCost(totalPrice);

        result.setReportDtoResList(reportDtoResList);

        return result;
    }

    @Override
    public boolean deleteOperationById(OperationsDto operationsDto) {
        if (operationsDto == null) {
            return false;
        }
        return operationsDAO.deleteOperationById(operationsDto.getOperationId());
    }
}
