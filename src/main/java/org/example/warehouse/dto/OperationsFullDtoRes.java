package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class OperationsFullDtoRes {
    private int rowNumber;
    private int operationId;
    private LocalDateTime operationDate;
    private String  typeName;
    private String invoiceNumber;
    private int productId;
    private String productName;
    private int productAmount;
    private String producerName;
    private String countryName;
    private int userId;
    private String firstName;
    private String lastName;
}
