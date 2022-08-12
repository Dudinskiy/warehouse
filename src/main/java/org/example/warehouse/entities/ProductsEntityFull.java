package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsEntityFull {
    private int rowNumber;
    private int productId;
    private String productName;
    private String producerName;
    private String countryName;
    private BigDecimal price;
    private int productAmount;
}
