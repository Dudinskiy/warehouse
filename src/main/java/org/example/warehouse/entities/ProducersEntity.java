package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducersEntity {
    private int rowNumber;
    private int producerId;
    private String producerName;
    private int countryId;
}
