package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.CountriesDto;
import org.example.warehouse.dto.CountriesDtoRes;
import org.example.warehouse.services.CountriesService;
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
@RequestMapping("/countries")
@AllArgsConstructor
public class CountriesController {
    private final CountriesService countriesService;


    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/add")
    public ModelAndView addCountry() {
        ModelAndView modelAndView = new ModelAndView("addCountry");
        modelAndView.addObject("countriesDto", new CountriesDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public ModelAndView createCountry(@ModelAttribute CountriesDto countriesDto) {
        String response;
        if (countriesService.createCountry(countriesDto)) {
            response = "Страна добавлена в базу данных";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        return new ModelAndView("addCountryRes", "response", response);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public ModelAndView getAllCountries() {
        ModelAndView modelAndView = new ModelAndView("getAllCountries");
        List<CountriesDtoRes> countriesDtoResList = countriesService.getAllCountries();
        modelAndView.addObject("countriesDtoResList", countriesDtoResList);
        return modelAndView;
    }

//    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
//    @PostMapping(value = "/get-by-id")
//    public ModelAndView getCountryById(@ModelAttribute CountriesDto countriesDto) {
//        ModelAndView modelAndView = new ModelAndView("getAllCountries");
//        List<CountriesDtoRes> countriesDtoResList = new ArrayList<>();
//        countriesDtoResList.add(countriesService.getCountryById(countriesDto));
//        modelAndView.addObject("countriesDtoResList", countriesDtoResList);
//        return modelAndView;
//    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-by-name-form")
    public ModelAndView getCountryByNameForm() {
        ModelAndView modelAndView = new ModelAndView("getCountryByNameForm");
        modelAndView.addObject("countriesDto", new CountriesDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public ModelAndView getCountryByName(@ModelAttribute CountriesDto countriesDto) {
        ModelAndView modelAndView = new ModelAndView("getAllCountries");
        List<CountriesDtoRes> countriesDtoResList = new ArrayList<>();
        countriesDtoResList.add(countriesService.getCountryByName(countriesDto));
        modelAndView.addObject("countriesDtoResList", countriesDtoResList);
        return modelAndView;
    }

}
