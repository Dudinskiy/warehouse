package org.example.warehouse.dao;

import org.example.warehouse.entities.CountriesEntity;

import java.util.List;

public interface CountriesDAO {
    boolean createCountry(String countryName);

    CountriesEntity getCountryById(int id);

    CountriesEntity getCountryByName(String name);

    List<CountriesEntity> getAllCountries();

    boolean deleteCountryById(int id);
}
