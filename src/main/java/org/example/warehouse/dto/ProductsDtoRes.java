package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductsDtoRes {
    private int rowNumber;
    private int productId;
    private String productName;
    private int producerId;
    private BigDecimal price;
    private int productAmount;
}
