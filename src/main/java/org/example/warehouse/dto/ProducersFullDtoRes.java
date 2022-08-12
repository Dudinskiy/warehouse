package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProducersFullDtoRes {
    private int rowNumber;
    private int producerId;
    private String producerName;
    private String countryName;
}
