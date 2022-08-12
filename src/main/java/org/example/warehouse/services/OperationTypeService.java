package org.example.warehouse.services;

import org.example.warehouse.dto.OperationTypeDto;
import org.example.warehouse.dto.OperationTypeDtoRes;

import java.util.List;

public interface OperationTypeService {
    boolean createOperationType(OperationTypeDto operationTypeDto);

    OperationTypeDtoRes getOperationTypeById(OperationTypeDto operationTypeDto);

    OperationTypeDtoRes getOperationTypeByName(OperationTypeDto operationTypeDto);

    List<OperationTypeDtoRes> getAllOperationType();

    boolean deleteOperationTypeById(OperationTypeDto operationTypeDto);
}
