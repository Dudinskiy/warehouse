package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsEntity {
    private int rowNumber;
    private int productId;
    private String productName;
    private int producerId;
    private BigDecimal price;
    private int productAmount;
}
