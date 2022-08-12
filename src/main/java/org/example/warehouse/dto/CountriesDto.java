package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.UnsupportedEncodingException;

@Data
@Accessors(chain = true)
public class CountriesDto {
    private int countryId;
    private String countryName;
}
