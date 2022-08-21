package org.example.warehouse.services;

import org.example.warehouse.dto.OperationTypeDto;
import org.example.warehouse.dto.OperationTypeDtoRes;

import java.util.List;

public interface OperationTypeService {
    boolean createOperationType(OperationTypeDto operationTypeDto);

    OperationTypeDtoRes getOperationTypeById(int id);

    OperationTypeDtoRes getOperationTypeByType(OperationTypeDto operationTypeDto);

    List<OperationTypeDtoRes> getAllOperationType();

    boolean deleteOperationTypeById(int id);
}
