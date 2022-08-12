package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsEntityFull {
    private int rowNumber;
    private int operationId;
    private LocalDateTime operationDate;
    private String  typeName;
    private String invoiceNumber;
    private int productId;
    private String productName;
    private BigDecimal price;
    private int productAmount;
    private String producerName;
    private String countryName;
    private int userId;
    private String firstName;
    private String lastName;
}
