package org.example.warehouse.dao;

import org.example.warehouse.entities.ProductsEntity;
import org.example.warehouse.entities.ProductsEntityFull;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsDAO {
    boolean createProduct(String productName, int producerId, BigDecimal price);

    boolean updateProductAmountById(int productId, int productAmount);

    ProductsEntity getProductById(int id);

    ProductsEntity getProductByName(String name);

    ProductsEntityFull getProductByNameFull(String name);

    ProductsEntityFull getProductByNameAndProducerFull(String name, String producer);

    ProductsEntityFull getProductByIdFull(int id);

    List<ProductsEntityFull> getAllProductFull();

    List<ProductsEntityFull> getAllProductFullByProducer(String prodName);

    boolean deleteProductById(int id);
}
