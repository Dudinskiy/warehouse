package org.example.warehouse.services;

import org.example.warehouse.dto.ProductsDto;
import org.example.warehouse.dto.ProductsDtoRes;
import org.example.warehouse.dto.ProductsFullDto;
import org.example.warehouse.dto.ProductsFullDtoRes;

import java.util.List;

public interface ProductsService {
    boolean createProduct(ProductsDto productsDto);

    ProductsDtoRes getProductById(ProductsDto productsDto);

    ProductsDtoRes getProductByName(ProductsDto productsDto);

    ProductsFullDtoRes getProductByNameFull(ProductsFullDto productsFullDto);

    List<ProductsFullDtoRes> getAllProductsFull();

    List<ProductsFullDtoRes> getAllProductFullByProducer(ProductsFullDto productsFullDto);

    boolean deleteProductById(ProductsDto productsDto);
}
