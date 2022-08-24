package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.*;
import org.example.warehouse.services.ProducersService;
import org.example.warehouse.services.ProductsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final ProducersService producersService;

    @Secured({"ROLE_Кладовщик"})
    @GetMapping(value = "/add")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("addProduct");
        List<ProducersFullDtoRes> producersFullDtoResList = producersService.getAllProducerFull();
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProducersList(producersFullDtoResList);
        modelAndView.addObject("productsDto", productsDto);
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/create")
    public ModelAndView createProduct(@ModelAttribute ProductsDto productsDto) {
        String response;
        if (productsService.createProduct(productsDto)) {
            response = "Товар добавлен в базу данных";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        return new ModelAndView("addProductRes", "response", response);
    }

    @Secured({"ROLE_Кладовщик"})
    @GetMapping(value = "/get-all")
    public ModelAndView getAllProductsFull() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        List<ProductsFullDtoRes> productsFullDtoResList = productsService.getAllProductsFull();
        modelAndView.addObject("productsFullDtoResList", productsFullDtoResList);
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Сотрудник"})
    @GetMapping(value = "/get-by-name-form")
    public ModelAndView getProductByNameForm() {
        ModelAndView modelAndView = new ModelAndView("getProductByNameForm");
        modelAndView.addObject("productsFullDto", new ProductsFullDto());
        return modelAndView;
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Сотрудник"})
    @PostMapping(value = "/get-by-name")
    public ModelAndView getProductByNameFull(@ModelAttribute ProductsFullDto productsFullDto) {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        List<ProductsFullDtoRes> productsFullDtoResList = new ArrayList<>();
        productsFullDtoResList.add(productsService.getProductByNameFull(productsFullDto));
        modelAndView.addObject("productsFullDtoResList", productsFullDtoResList);
        return modelAndView;
    }

    @CrossOrigin
    @Secured({"ROLE_Кладовщик", "ROLE_Сотрудник"})
    @PostMapping(value = "/get-by-producer")
    public ModelAndView getAllProductsByProducer(@ModelAttribute ProductsFullDto productsFullDto) {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        List<ProductsFullDtoRes> productsFullDtoResList = productsService
                .getAllProductFullByProducer(productsFullDto);
        modelAndView.addObject("productsFullDtoResList", productsFullDtoResList);
        return modelAndView;
    }

    public ModelAndView deleteProductById(ProductsDto productsDto) {
        return null;
    }
}
