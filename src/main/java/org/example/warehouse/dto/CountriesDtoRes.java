package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CountriesDtoRes {
    private int rowNumber;
    private int countryId;
    private String countryName;
}
