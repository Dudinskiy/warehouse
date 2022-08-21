package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.CountriesDAO;
import org.example.warehouse.dto.CountriesDto;
import org.example.warehouse.dto.CountriesDtoRes;
import org.example.warehouse.dto.OperationTypeDtoRes;
import org.example.warehouse.entities.CountriesEntity;
import org.example.warehouse.entities.OperationTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CountriesServiceImpl implements CountriesService {
    private final CountriesDAO countriesDAO;


    @Override
    @Transactional
    public boolean createCountry(CountriesDto countriesDto) {
        if (countriesDto == null) {
            return false;
        }
        return countriesDAO.createCountry(countriesDto.getCountryName());
    }

    @Override
    public CountriesDtoRes getCountryById(int id) {
        if (id == 0) {
            return null;
        }
        CountriesEntity country = countriesDAO.getCountryById(id);
        CountriesDtoRes countriesDtoRes = null;
        if (country != null) {
            countriesDtoRes = new CountriesDtoRes()
                    .setRowNumber(country.getRowNumber())
                    .setCountryId(country.getCountryId())
                    .setCountryName(country.getCountryName());
        }
        return countriesDtoRes;
    }

    @Override
    public CountriesDtoRes getCountryByName(CountriesDto countriesDto) {
        if (countriesDto == null) {
            return null;
        }
        CountriesEntity country = countriesDAO.getCountryByName(countriesDto.getCountryName());
        CountriesDtoRes countriesDtoRes = null;
        if (country != null) {
            countriesDtoRes = new CountriesDtoRes()
                    .setRowNumber(country.getRowNumber())
                    .setCountryId(country.getCountryId())
                    .setCountryName(country.getCountryName());
        }
        return countriesDtoRes;
    }

    @Override
    public List<CountriesDtoRes> getAllCountries() {
        List<CountriesEntity> entityList = countriesDAO.getAllCountries();
        List<CountriesDtoRes> dtoResList = new ArrayList<>();

        for (CountriesEntity entity : entityList) {
            dtoResList.add(new CountriesDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setCountryId(entity.getCountryId())
                    .setCountryName(entity.getCountryName()));
        }
        return dtoResList;
    }

    @Override
    public boolean deleteCountryById(int id) {
        if (id == 0) {
            return false;
        }
        return countriesDAO.deleteCountryById(id);
    }
}
