package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OperationTypeDtoRes {
    private int rowNumber;
    private int typeId;
    private String typeName;
}
