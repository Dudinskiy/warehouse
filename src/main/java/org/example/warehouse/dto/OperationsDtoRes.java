package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class OperationsDtoRes {
    private int rowNumber;
    private int operationId;
    private LocalDateTime operationDate;
    private int typeId;
    private String invoiceNumber;
}
