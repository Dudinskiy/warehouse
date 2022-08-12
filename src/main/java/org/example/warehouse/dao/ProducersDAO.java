package org.example.warehouse.dao;

import org.example.warehouse.entities.ProducersEntity;
import org.example.warehouse.entities.ProducersEntityFull;

import java.util.List;

public interface ProducersDAO {
    boolean createProducer(String producerName, int countryId);

    ProducersEntity getProducerById(int id);

    ProducersEntity getProducerByName(String name);

    ProducersEntityFull getProducerByNameFull(String name);

    List<ProducersEntityFull> getAllProducerFull();

    boolean deleteProducerById(int id);
}
