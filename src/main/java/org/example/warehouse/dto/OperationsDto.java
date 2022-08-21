package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class OperationsDto {
    private int operationId;
    private List<ProducersFullDtoRes> allProducers;
    private List<ProductOrderDto> productList;
    private List<OperationTypeDtoRes> operationTypeList;
    private List<ProductsFullDtoRes> allProducts;
    private String typeName;
    private String type;
    private String invoiceNumber;
    private int productId;
    private int operationProdAmount;
    private int currentProdAmount;
    private String producerName;
    private String productName;
    private LocalDate start;
    private LocalDate end;
    private String start1;
    private String end1;
    private String login;
}
