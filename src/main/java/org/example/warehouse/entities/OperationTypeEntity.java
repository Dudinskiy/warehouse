package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationTypeEntity {
    private int rowNumber;
    private int typeId;
    private String typeName;
    private String type;
}
