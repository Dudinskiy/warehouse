package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductOrderDto {
    private int rowNumber;
    private int productId;
    private String productName;
    private String producerName;
    private int operationProdAmount;
    private int currentProdAmount;
}
