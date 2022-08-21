package org.example.warehouse.services;

import org.example.warehouse.dto.OperationReportDtoRes;
import org.example.warehouse.dto.OperationsDto;
import org.example.warehouse.dto.OperationsDtoRes;
import org.example.warehouse.dto.OperationsFullDtoRes;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OperationsService {
    boolean createOperation(OperationsDto operationsDto);

    OperationsDtoRes getOperationById(int id);

    OperationsDtoRes getOperationByInvoice(OperationsDto operationsDto);

    List<OperationsFullDtoRes> getOperationByInvoiceFull(OperationsDto operationsDto);

    OperationReportDtoRes getOperationReportByDay();

    OperationReportDtoRes getOperationReportByPeriod(OperationsDto operationsDto);

    OperationReportDtoRes getOperationReportByDayAndTypeFull(OperationsDto operationsDto);

    OperationReportDtoRes getOperationReportByPeriodAndTypeFull(OperationsDto operationsDto);

    boolean deleteOperationById(int id);
}
