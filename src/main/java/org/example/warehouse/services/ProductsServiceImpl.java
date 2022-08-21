package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.ProducersDAO;
import org.example.warehouse.dao.ProductsDAO;
import org.example.warehouse.dto.ProductsDto;
import org.example.warehouse.dto.ProductsDtoRes;
import org.example.warehouse.dto.ProductsFullDto;
import org.example.warehouse.dto.ProductsFullDtoRes;
import org.example.warehouse.entities.ProducersEntity;
import org.example.warehouse.entities.ProductsEntity;
import org.example.warehouse.entities.ProductsEntityFull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductsDAO productsDAO;
    private final ProducersDAO producersDAO;

    @Override
    @Transactional
    public boolean createProduct(ProductsDto productsDto) {
        if (productsDto == null) {
            return false;
        }
        ProducersEntity entity = producersDAO.getProducerByName(productsDto.getProducerName());
        if (entity != null) {
            return productsDAO.createProduct(productsDto.getProductName()
                    , entity.getProducerId()
                    , productsDto.getPrice());
        }
        return false;
    }

    @Override
    public ProductsDtoRes getProductById(int id) {
        if (id == 0) {
            return null;
        }
        ProductsEntity entity = productsDAO.getProductById(id);
        ProductsDtoRes productsDtoRes = null;
        if (entity != null) {
            productsDtoRes = new ProductsDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProductId(entity.getProductId())
                    .setProductName(entity.getProductName())
                    .setProducerId(entity.getProducerId())
                    .setPrice(entity.getPrice())
                    .setProductAmount(entity.getProductAmount());
        }
        return productsDtoRes;
    }

    @Override
    public ProductsDtoRes getProductByName(ProductsDto productsDto) {
        if (productsDto == null) {
            return null;
        }
        ProductsEntity entity = productsDAO.getProductByName(productsDto.getProductName());
        ProductsDtoRes productsDtoRes = null;
        if (entity != null) {
            productsDtoRes = new ProductsDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProductId(entity.getProductId())
                    .setProductName(entity.getProductName())
                    .setProducerId(entity.getProducerId())
                    .setPrice(entity.getPrice())
                    .setProductAmount(entity.getProductAmount());
        }
        return productsDtoRes;
    }

    @Override
    public ProductsFullDtoRes getProductByNameFull(ProductsFullDto productsFullDto) {
        if (productsFullDto == null) {
            return null;
        }
        ProductsEntityFull entity = productsDAO
                .getProductByNameFull(productsFullDto.getProductName());
        ProductsFullDtoRes product = null;
        if (entity != null) {
            product = new ProductsFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProductId(entity.getProductId())
                    .setProductName(entity.getProductName())
                    .setProducerName(entity.getProducerName())
                    .setCountryName(entity.getCountryName())
                    .setPrice(entity.getPrice())
                    .setProductAmount(entity.getProductAmount());
        }
        return product;
    }

    @Override
    public List<ProductsFullDtoRes> getAllProductsFull() {
        List<ProductsFullDtoRes> productsList = new ArrayList<>();
        List<ProductsEntityFull> entityList = productsDAO.getAllProductFull();

        for (ProductsEntityFull entity : entityList) {
            ProductsFullDtoRes product = new ProductsFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProductId(entity.getProductId())
                    .setProductName(entity.getProductName())
                    .setProducerName(entity.getProducerName())
                    .setCountryName(entity.getCountryName())
                    .setPrice(entity.getPrice())
                    .setProductAmount(entity.getProductAmount());

            productsList.add(product);
        }
        return productsList;
    }

    @Override
    public List<ProductsFullDtoRes> getAllProductFullByProducer(ProductsFullDto productsFullDto) {
        List<ProductsFullDtoRes> productsList = new ArrayList<>();
        List<ProductsEntityFull> entityList = productsDAO
                .getAllProductFullByProducer(productsFullDto.getProducerName());

        for (ProductsEntityFull entity : entityList) {
            ProductsFullDtoRes product = new ProductsFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProductId(entity.getProductId())
                    .setProductName(entity.getProductName())
                    .setProducerName(entity.getProducerName())
                    .setCountryName(entity.getCountryName())
                    .setPrice(entity.getPrice())
                    .setProductAmount(entity.getProductAmount());

            productsList.add(product);
        }
        return productsList;

    }

    @Override
    public boolean deleteProductById(int id) {
        if (id == 0) {
            return false;
        }
        return productsDAO.deleteProductById(id);
    }
}
