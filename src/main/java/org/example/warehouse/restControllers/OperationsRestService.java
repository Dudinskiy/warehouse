package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.OperationReportDtoRes;
import org.example.warehouse.dto.OperationsDto;
import org.example.warehouse.dto.OperationsDtoRes;
import org.example.warehouse.dto.OperationsFullDtoRes;
import org.example.warehouse.services.OperationsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/operation")
@AllArgsConstructor
public class OperationsRestService {

    private final OperationsService operationsService;

    @PostMapping(value = "/create")
    public boolean createOperation(@RequestBody OperationsDto operationsDto) {
        return operationsService.createOperation(operationsDto);
    }

    @PostMapping(value = "/get-by-id")
    public OperationsDtoRes getOperationById(@RequestBody OperationsDto operationsDto) {
        return operationsService.getOperationById(operationsDto);
    }

    @PostMapping(value = "/get-by-invoice")
    public OperationsDtoRes getOperationByInvoice(@RequestBody OperationsDto operationsDto) {
        return operationsService.getOperationByInvoice(operationsDto);
    }

    @PostMapping(value = "get-by-invoice-full")
    public List<OperationsFullDtoRes> getOperationByInvoiceFull(
            @RequestBody  OperationsDto operationsDto){
        return operationsService.getOperationByInvoiceFull(operationsDto);
    }

    @GetMapping("/report-by-day")
    public OperationReportDtoRes getOperationReportByDay(){
        return operationsService.getOperationReportByDay();
    }

    @PostMapping(value = "/report-by-period")
    public OperationReportDtoRes getOperationReportByPeriod(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByPeriod(operationsDto);
    }

    @PostMapping(value = "/report-by-day-type")
    public OperationReportDtoRes getOperationReportByDayAndTypeFull(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByDayAndTypeFull(operationsDto);
    }

    @PostMapping(value = "/report-by-period-type")
    public OperationReportDtoRes getOperationReportByPeriodAndTypeFull(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByPeriodAndTypeFull(operationsDto);
    }

    @PostMapping(value = "/delete")
    public boolean deleteOperationById(@RequestBody OperationsDto operationsDto) {
        return operationsService.deleteOperationById(operationsDto);
    }
}