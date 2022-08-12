package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductsFullDtoRes {
    private int rowNumber;
    private int productId;
    private String productName;
    private String producerName;
    private String countryName;
    private BigDecimal price;
    private int productAmount;
}
