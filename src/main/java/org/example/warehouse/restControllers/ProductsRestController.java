package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.ProductsDto;
import org.example.warehouse.dto.ProductsDtoRes;
import org.example.warehouse.dto.ProductsFullDto;
import org.example.warehouse.dto.ProductsFullDtoRes;
import org.example.warehouse.services.ProductsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rest/product")
@AllArgsConstructor
public class ProductsRestController {

    private final ProductsService productsService;

    @PostMapping(value = "/create")
    public boolean createProduct(@RequestBody ProductsDto productsDto) {
        return productsService.createProduct(productsDto);
    }

    @PostMapping(value = "/get-by-id")
    public ProductsDtoRes getProductById(@RequestBody ProductsDto productsDto) {
        return productsService.getProductById(productsDto);
    }

    @PostMapping(value = "/get-by-name")
    public ProductsDtoRes getProductByName(@RequestBody ProductsDto productsDto) {
        return productsService.getProductByName(productsDto);
    }

    @GetMapping(value = "/get-all")
    public List<ProductsFullDtoRes> getAllProductsFull(){
        return productsService.getAllProductsFull();
    }

    @PostMapping(value = "/get-by-producer")
    public List<ProductsFullDtoRes> getAllProductsFullByProducer(
            @RequestBody ProductsFullDto productsFullDto){
        return productsService.getAllProductFullByProducer(productsFullDto);
    }

    @PostMapping(value = "/delete")
    public boolean deleteProductById(@RequestBody ProductsDto productsDto) {
        return productsService.deleteProductById(productsDto);
    }


}
