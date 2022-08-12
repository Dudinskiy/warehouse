package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsEntity {
    private int rowNumber;
    private int operationId;
    private LocalDateTime operationDate;
    private int typeId;
    private String invoiceNumber;
    private int userId;
}
