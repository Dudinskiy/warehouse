package org.example.warehouse.services;

import org.example.warehouse.dto.ProducersDto;
import org.example.warehouse.dto.ProducersDtoRes;
import org.example.warehouse.dto.ProducersFullDto;
import org.example.warehouse.dto.ProducersFullDtoRes;

import java.util.List;

public interface ProducersService {
    boolean createProducer(ProducersDto producersDto);

    ProducersDtoRes getProducerById(int id);

    ProducersDtoRes getProducerByName(ProducersDto producersDto);

    ProducersFullDtoRes getProducerByNameFull(ProducersFullDto producersFullDto);

    List<ProducersFullDtoRes> getAllProducerFull();

    boolean deleteProducerById(int id);
}
