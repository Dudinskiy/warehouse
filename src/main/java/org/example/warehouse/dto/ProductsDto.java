package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductsDto {
    private int productId;
    private String productName;
    private String producerName;
    private BigDecimal price;
    private int productAmount;
    private List<ProducersFullDtoRes> producersList;
}
