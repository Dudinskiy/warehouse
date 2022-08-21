package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationTypeEntity;

import java.util.List;

public interface OperationTypeDAO {
    boolean createOperationType(String typeName);

    OperationTypeEntity getOperationTypeById(int id);

    OperationTypeEntity getOperationTypeByType(String type);

    List<OperationTypeEntity> getAllOperationType();

    boolean deleteOperationTypeById(int id);
}
