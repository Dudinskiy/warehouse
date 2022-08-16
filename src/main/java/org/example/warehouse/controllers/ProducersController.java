package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.*;
import org.example.warehouse.services.CountriesService;
import org.example.warehouse.services.ProducersService;
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
@RequestMapping("/producers")
@AllArgsConstructor
public class ProducersController {

    private final ProducersService producersService;
    private final CountriesService countriesService;

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/add")
    public ModelAndView addProducer() {
        ModelAndView modelAndView = new ModelAndView("addProducer");
        List<CountriesDtoRes> countriesDtoList = countriesService.getAllCountries();
        ProducersDto producersDto = new ProducersDto();
        producersDto.setCountriesList(countriesDtoList);
        modelAndView.addObject("producersDto", producersDto);
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public ModelAndView createProducer(@ModelAttribute ProducersDto producersDto) {
        String response;
        if (producersService.createProducer(producersDto)) {
            response = "Производитель добавлен в базу данных";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        return new ModelAndView("addProducerRes", "response", response);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public ModelAndView getAllProducers() {
        ModelAndView modelAndView = new ModelAndView("getAllProducers");
        List<ProducersFullDtoRes> producersFullDtoResList = producersService.getAllProducerFull();
        modelAndView.addObject("producersFullDtoResList", producersFullDtoResList);
        return modelAndView;
    }

    public ModelAndView getProducerById(ProducersDto producersDto) {
        return null;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-by-name-form")
    public ModelAndView getCountryByNameForm() {
        ModelAndView modelAndView = new ModelAndView("getProducerByNameForm");
        modelAndView.addObject("producersFullDto", new ProducersFullDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public ModelAndView getProducerByNameFull(@ModelAttribute ProducersFullDto producersFullDto) {
        ModelAndView modelAndView = new ModelAndView("getAllProducers");
        List<ProducersFullDtoRes> producersFullDtoResList = new ArrayList<>();
        producersFullDtoResList.add(producersService.getProducerByNameFull(producersFullDto));
        modelAndView.addObject("producersFullDtoResList", producersFullDtoResList);
        return modelAndView;
    }

    public ModelAndView deleteProducerById(ProducersDto producersDto) {
        return null;
    }

}
