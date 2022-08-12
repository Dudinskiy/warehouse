package org.example.warehouse.services;

import org.example.warehouse.dto.CountriesDto;
import org.example.warehouse.dto.CountriesDtoRes;

import java.util.List;

public interface CountriesService {
    boolean createCountry(CountriesDto countriesDto);

    CountriesDtoRes getCountryById(CountriesDto countriesDto);

    CountriesDtoRes getCountryByName(CountriesDto countriesDto);

    List<CountriesDtoRes> getAllCountries();

    boolean deleteCountryById(CountriesDto countriesDto);
}
