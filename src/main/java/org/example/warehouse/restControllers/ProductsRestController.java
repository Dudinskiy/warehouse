package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.ProductsDto;
import org.example.warehouse.dto.ProductsDtoRes;
import org.example.warehouse.dto.ProductsFullDto;
import org.example.warehouse.dto.ProductsFullDtoRes;
import org.example.warehouse.services.ProductsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rest/product")
@AllArgsConstructor
public class ProductsRestController {

    private final ProductsService productsService;

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/create")
    public boolean createProduct(@RequestBody ProductsDto productsDto) {
        return productsService.createProduct(productsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @GetMapping(value = "/get-by-id/{id}")
    public ProductsDtoRes getProductById(@PathVariable int id) {
        return productsService.getProductById(id);
    }

    @Secured({"ROLE_Кладовщик"})
    @PostMapping(value = "/get-by-name")
    public ProductsDtoRes getProductByName(@RequestBody ProductsDto productsDto) {
        return productsService.getProductByName(productsDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @GetMapping(value = "/get-all")
    public List<ProductsFullDtoRes> getAllProductsFull() {
        return productsService.getAllProductsFull();
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Сотрудник"})
    @PostMapping(value = "/get-by-producer")
    public List<ProductsFullDtoRes> getAllProductsFullByProducer(
            @RequestBody ProductsFullDto productsFullDto) {
        return productsService.getAllProductFullByProducer(productsFullDto);
    }

    @Secured({"ROLE_Кладовщик"})
    @DeleteMapping(value = "/delete/{id}")
    public boolean deleteProductById(@PathVariable int id) {
        return productsService.deleteProductById(id);
    }
}
