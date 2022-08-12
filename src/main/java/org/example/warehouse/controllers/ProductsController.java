package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.*;
import org.example.warehouse.services.ProducersService;
import org.example.warehouse.services.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final ProducersService producersService;

    @GetMapping(value = "/add")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("addProduct");
        List<ProducersFullDtoRes> producersFullDtoResList = producersService.getAllProducerFull();
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProducersList(producersFullDtoResList);
        modelAndView.addObject("productsDto", productsDto);
        return modelAndView;
    }

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

    @GetMapping(value = "/get-all")
    public ModelAndView getAllProductsFull() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        List<ProductsFullDtoRes> productsFullDtoResList = productsService.getAllProductsFull();
        modelAndView.addObject("productsFullDtoResList", productsFullDtoResList);
        return modelAndView;
    }

    public ModelAndView getAllProductsFullByProducer(){
        return null;
    }

    public ModelAndView getProductById(ProductsDto productsDto) {
        return null;
    }

    @GetMapping(value = "/get-by-name-form")
    public ModelAndView getProductByNameForm() {
        ModelAndView modelAndView = new ModelAndView("getProductByNameForm");
        modelAndView.addObject("productsFullDto", new  ProductsFullDto());
        return modelAndView;
    }

    @PostMapping(value = "/get-by-name")
    public ModelAndView getProductByNameFull(@ModelAttribute ProductsFullDto productsFullDto) {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        List<ProductsFullDtoRes> productsFullDtoResList = new ArrayList<>();
        productsFullDtoResList.add(productsService.getProductByNameFull(productsFullDto));
        modelAndView.addObject("productsFullDtoResList", productsFullDtoResList);
        return modelAndView;
    }

    public ModelAndView deleteProductById(ProductsDto productsDto) {
        return null;
    }
}
