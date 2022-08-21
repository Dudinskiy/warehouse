package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OperationTypeDto {
    private int typeId;
    private String typeName;
    private String type;
}
