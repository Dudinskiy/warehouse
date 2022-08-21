package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.CountriesDAO;
import org.example.warehouse.dao.ProducersDAO;
import org.example.warehouse.dto.ProducersDto;
import org.example.warehouse.dto.ProducersDtoRes;
import org.example.warehouse.dto.ProducersFullDto;
import org.example.warehouse.dto.ProducersFullDtoRes;
import org.example.warehouse.entities.CountriesEntity;
import org.example.warehouse.entities.ProducersEntity;
import org.example.warehouse.entities.ProducersEntityFull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProducersServiceImpl implements ProducersService {
    private final ProducersDAO producersDAO;
    private final CountriesDAO countriesDAO;

    @Override
    @Transactional
    public boolean createProducer(ProducersDto producersDto) {
        if (producersDto == null) {
            return false;
        }
        CountriesEntity country = countriesDAO.getCountryByName(producersDto.getCountryName());
        if (country != null) {
            return producersDAO.createProducer(producersDto.getProducerName()
                    , country.getCountryId());
        }
        return false;
    }

    @Override
    public ProducersDtoRes getProducerById(int id) {
        if (id == 0) {
            return null;
        }
        ProducersEntity entity = producersDAO.getProducerById(id);
        ProducersDtoRes producersDtoRes = null;
        if (entity != null) {
            producersDtoRes = new ProducersDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProducerId(entity.getProducerId())
                    .setProducerName(entity.getProducerName())
                    .setCountryId(entity.getCountryId());
        }
        return producersDtoRes;
    }

    @Override
    public ProducersDtoRes getProducerByName(ProducersDto producersDto) {
        if (producersDto == null) {
            return null;
        }
        ProducersEntity entity = producersDAO.getProducerByName(producersDto.getProducerName());
        ProducersDtoRes producersDtoRes = null;
        if (entity != null) {
            producersDtoRes = new ProducersDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProducerId(entity.getProducerId())
                    .setProducerName(entity.getProducerName())
                    .setCountryId(entity.getCountryId());
        }
        return producersDtoRes;
    }

    @Override
    public ProducersFullDtoRes getProducerByNameFull(ProducersFullDto producersFullDto) {
        if (producersFullDto == null) {
            return null;
        }
        ProducersEntityFull entity = producersDAO
                .getProducerByNameFull(producersFullDto.getProducerName());
        ProducersFullDtoRes producersFullDtoRes = null;
        if (entity != null) {
            producersFullDtoRes = new ProducersFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProducerId(entity.getProducerId())
                    .setProducerName(entity.getProducerName())
                    .setCountryName(entity.getCountryName());
        }
        return producersFullDtoRes;
    }

    @Override
    public List<ProducersFullDtoRes> getAllProducerFull() {
        List<ProducersEntityFull> entityFullList = producersDAO.getAllProducerFull();
        List<ProducersFullDtoRes> producersFullDtoResList = new ArrayList<>();
        for (ProducersEntityFull entity : entityFullList) {
            producersFullDtoResList.add(new ProducersFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setProducerId(entity.getProducerId())
                    .setProducerName(entity.getProducerName())
                    .setCountryName(entity.getCountryName()));
        }
        return producersFullDtoResList;
    }

    @Override
    public boolean deleteProducerById(int id) {
        if (id == 0) {
            return false;
        }
        return producersDAO.deleteProducerById(id);
    }
}
