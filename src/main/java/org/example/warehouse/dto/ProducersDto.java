package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProducersDto {
    private int producerId;
    private String producerName;
    private String countryName;
    private List<CountriesDtoRes> countriesList;
}
