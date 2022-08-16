package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.*;
import org.example.warehouse.services.OperationTypeService;
import org.example.warehouse.services.OperationsService;
import org.example.warehouse.services.ProducersService;
import org.example.warehouse.services.ProductsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/operations")
@AllArgsConstructor
public class OperationsController {

    private final OperationsService operationsService;
    private final OperationTypeService operationTypeService;
    private final ProductsService productsService;
    private final ProducersService producersService;
    private List<ProductOrderDto> productOrderDtoList;
    private List<ProductsFullDtoRes> allProducts;

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/order-form")
    public ModelAndView addProductOrderForm() {
        ModelAndView modelAndView = new ModelAndView("addProductOrderForm");
        OperationsDto operationsDto = new OperationsDto();
        allProducts = productsService.getAllProductsFull();
        List<ProducersFullDtoRes> allProducers = producersService.getAllProducerFull();
        operationsDto.setAllProducers(allProducers);
        operationsDto.setAllProducts(allProducts);
        operationsDto.setProductList(productOrderDtoList);
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/add-order")
    public ModelAndView addProductOrder(@ModelAttribute OperationsDto operationsDto) {
        ModelAndView modelAndView = new ModelAndView("redirect:/operations/order-form");

        ProductsFullDto productsFullDto = new ProductsFullDto()
                .setProductName(operationsDto.getProductName())
                .setProducerName(operationsDto.getProducerName());

        ProductsFullDtoRes productsFullDtoRes = productsService.getProductByNameFull(productsFullDto);

        ProductOrderDto productOrderDto = new ProductOrderDto();
        productOrderDto.setProductId(productsFullDtoRes.getProductId())
                .setProductName(productsFullDtoRes.getProductName())
                .setProducerName(productsFullDtoRes.getProducerName())
                .setCurrentProdAmount(productsFullDtoRes.getProductAmount())
                .setOperationProdAmount(operationsDto.getOperationProdAmount());
        if (!productOrderDtoList.contains(productOrderDto)) {
            productOrderDtoList.add(productOrderDto);
        }
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/delete-order/{productId}")
    public ModelAndView deleteProductOrder(@PathVariable int productId) {
        for (int i = 0; i < productOrderDtoList.size(); i++) {
            if (productOrderDtoList.get(i).getProductId() == productId) {
                productOrderDtoList.remove(i);
            }
        }
        return new ModelAndView("redirect:/operations/order-form");
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/edit-order/{productId}")
    public ModelAndView editProductOrder(@PathVariable int productId) {
        for (int i = 0; i < productOrderDtoList.size(); i++) {
            if (productOrderDtoList.get(i).getProductId() == productId) {
                productOrderDtoList.remove(i);
            }
        }
        return new ModelAndView("redirect:/operations/order-form");
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/operation-form")
    public ModelAndView createOperationForm() {
        ModelAndView modelAndView = new ModelAndView("addOperation");
        OperationsDto operationsDto = new OperationsDto();
        operationsDto.setProductList(productOrderDtoList);
        List<OperationTypeDtoRes> operationTypeDtoResList = operationTypeService.getAllOperationType();
        operationsDto.setOperationTypeList(operationTypeDtoResList);
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/create")
    public ModelAndView createOperation(@ModelAttribute OperationsDto operationsDto) {
        ModelAndView modelAndView = new ModelAndView("getOperationRes");
        operationsDto.setProductList(productOrderDtoList);
        operationsService.createOperation(operationsDto);
        List<OperationsFullDtoRes> operationList = operationsService
                .getOperationByInvoiceFull(operationsDto);
        modelAndView.addObject("operationList", operationList);
        productOrderDtoList.clear();
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/get-by-invoice-form")
    public ModelAndView getOperationByInvoiceFullForm() {
        ModelAndView modelAndView = new ModelAndView("getOperationByInvoiceForm");
        OperationsDto operationsDto = new OperationsDto();
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/get-by-invoice")
    public ModelAndView getOperationByInvoiceFull(@ModelAttribute OperationsDto operationsDto) {
        ModelAndView modelAndView = new ModelAndView("getOperationRes");
        List<OperationsFullDtoRes> operationList = operationsService
                .getOperationByInvoiceFull(operationsDto);
        modelAndView.addObject("operationList", operationList);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/report-by-day")
    public ModelAndView getOperationReportByDay() {
        ModelAndView modelAndView = new ModelAndView("getOperationReportByDay");
        OperationReportDtoRes operationReportDtoRes = operationsService.getOperationReportByDay();
        modelAndView.addObject("operationReportDtoRes", operationReportDtoRes);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/report-by-period-form")
    public ModelAndView getOperationReportByPeriodForm() {
        ModelAndView modelAndView = new ModelAndView("getOperationReportForm");
        OperationsDto operationsDto = new OperationsDto();
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/report-by-period")
    public ModelAndView getOperationReportByPeriod(@ModelAttribute OperationsDto operationsDto) {
        LocalDate start = LocalDate.parse(operationsDto.getStart1());
        LocalDate end = LocalDate.parse(operationsDto.getEnd1());
        operationsDto.setStart(start);
        operationsDto.setEnd(end);
        System.out.println(operationsDto);
        OperationReportDtoRes operationReportDtoRes = operationsService
                .getOperationReportByPeriod(operationsDto);
        ModelAndView modelAndView = new ModelAndView("getOperationReportByDay");
        modelAndView.addObject("operationReportDtoRes", operationReportDtoRes);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/report-by-day-type-form")
    public ModelAndView getOperationReportByDayAndTypeForm() {
        ModelAndView modelAndView = new ModelAndView("getOperationReportByDayAndTypeForm");
        OperationsDto operationsDto = new OperationsDto();
        List<OperationTypeDtoRes> operationTypeDtoResList = operationTypeService.getAllOperationType();
        operationsDto.setOperationTypeList(operationTypeDtoResList);
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/report-by-day-type")
    public ModelAndView getOperationReportByDayAndType(@ModelAttribute OperationsDto operationsDto) {
        OperationReportDtoRes operationReportDtoRes = operationsService
                .getOperationReportByDayAndTypeFull(operationsDto);
        ModelAndView modelAndView = new ModelAndView("getOperationReportByDayAndType");
        modelAndView.addObject("operationReportDtoRes", operationReportDtoRes);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @GetMapping(value = "/report-by-period-type-form")
    public ModelAndView getOperationReportByPeriodAndTypeForm() {
        ModelAndView modelAndView = new ModelAndView("getOperationReportByPeriodAndTypeForm");
        OperationsDto operationsDto = new OperationsDto();
        List<OperationTypeDtoRes> operationTypeDtoResList = operationTypeService.getAllOperationType();
        operationsDto.setOperationTypeList(operationTypeDtoResList);
        modelAndView.addObject("operationsDto", operationsDto);
        return modelAndView;
    }

    @Secured("ROLE_Кладовщик")
    @PostMapping(value = "/report-by-period-type")
    public ModelAndView getOperationReportByPeriodAndType(@ModelAttribute OperationsDto operationsDto) {
        LocalDate start = LocalDate.parse(operationsDto.getStart1());
        LocalDate end = LocalDate.parse(operationsDto.getEnd1());
        operationsDto.setStart(start);
        operationsDto.setEnd(end);
        System.out.println(operationsDto);
        OperationReportDtoRes operationReportDtoRes = operationsService
                .getOperationReportByPeriodAndTypeFull(operationsDto);
        ModelAndView modelAndView = new ModelAndView("getOperationReportByPeriodAndType");
        modelAndView.addObject("operationReportDtoRes", operationReportDtoRes);
        return modelAndView;
    }

}
