package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.CountriesDto;
import org.example.warehouse.dto.OperationTypeDto;
import org.example.warehouse.dto.OperationTypeDtoRes;
import org.example.warehouse.services.OperationTypeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/operation-type")
@AllArgsConstructor
public class OperationTypeController {
    private final OperationTypeService operationTypeService;

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/add")
    public ModelAndView addOperationType() {
        ModelAndView modelAndView = new ModelAndView("addOperationType");
        modelAndView.addObject("operationTypeDto", new OperationTypeDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public ModelAndView createOperationType(@ModelAttribute OperationTypeDto operationTypeDto) {
        String response;
        if (operationTypeService.createOperationType(operationTypeDto)) {
            response = "Новый вид операции добавлен";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        return new ModelAndView("addOperationTypeRes", "response", response);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public ModelAndView getAllOperationType() {
        ModelAndView modelAndView = new ModelAndView("getAllOperationType");
        List<OperationTypeDtoRes> operationTypeDtoResList = operationTypeService.getAllOperationType();
        modelAndView.addObject("operationTypeDtoResList", operationTypeDtoResList);
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-id")
    public ModelAndView getOperationTypeById(@ModelAttribute OperationTypeDto operationTypeDto) {
        ModelAndView modelAndView = new ModelAndView("getAllOperationType");
        List<OperationTypeDtoRes> operationTypeDtoResList = new ArrayList<>();
        operationTypeDtoResList.add(operationTypeService.getOperationTypeById(operationTypeDto));
        modelAndView.addObject("operationTypeDtoResList", operationTypeDtoResList);
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-by-name-form")
    public ModelAndView getOperationTypeByNameForm() {
        ModelAndView modelAndView = new ModelAndView("getOperationTypeByNameForm");
        modelAndView.addObject("operationTypeDto", new OperationTypeDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public ModelAndView getOperationTypeByName(@ModelAttribute OperationTypeDto operationTypeDto) {
        ModelAndView modelAndView = new ModelAndView("getAllOperationType");
        List<OperationTypeDtoRes> operationTypeDtoResList = new ArrayList<>();
        operationTypeDtoResList.add(operationTypeService.getOperationTypeByName(operationTypeDto));
        modelAndView.addObject("operationTypeDtoResList", operationTypeDtoResList);
        return modelAndView;
    }

    public ModelAndView deleteOperationTypeById(OperationTypeDto operationTypeDto) {
        return null;
    }
}
