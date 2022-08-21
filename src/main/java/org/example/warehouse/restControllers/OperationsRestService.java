package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.OperationReportDtoRes;
import org.example.warehouse.dto.OperationsDto;
import org.example.warehouse.dto.OperationsDtoRes;
import org.example.warehouse.dto.OperationsFullDtoRes;
import org.example.warehouse.services.OperationsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/operation")
@AllArgsConstructor
public class OperationsRestService {

    private final OperationsService operationsService;

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/create")
    public boolean createOperation(@RequestBody OperationsDto operationsDto) {
        return operationsService.createOperation(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/get-by-id/{id}")
    public OperationsDtoRes getOperationById(@PathVariable int id) {
        return operationsService.getOperationById(id);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/get-by-invoice")
    public OperationsDtoRes getOperationByInvoice(@RequestBody OperationsDto operationsDto) {
        return operationsService.getOperationByInvoice(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "get-by-invoice-full")
    public List<OperationsFullDtoRes> getOperationByInvoiceFull(
            @RequestBody  OperationsDto operationsDto){
        return operationsService.getOperationByInvoiceFull(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @GetMapping("/report-by-day")
    public OperationReportDtoRes getOperationReportByDay(){
        return operationsService.getOperationReportByDay();
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/report-by-period")
    public OperationReportDtoRes getOperationReportByPeriod(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByPeriod(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/report-by-day-type")
    public OperationReportDtoRes getOperationReportByDayAndTypeFull(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByDayAndTypeFull(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/report-by-period-type")
    public OperationReportDtoRes getOperationReportByPeriodAndTypeFull(
            @RequestBody OperationsDto operationsDto){
        return operationsService.getOperationReportByPeriodAndTypeFull(operationsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @DeleteMapping(value = "/delete/{id}")
    public boolean deleteOperationById(@PathVariable int id) {
        return operationsService.deleteOperationById(id);
    }
}
