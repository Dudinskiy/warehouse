package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.OperationTypeDAO;
import org.example.warehouse.dto.OperationTypeDto;
import org.example.warehouse.dto.OperationTypeDtoRes;
import org.example.warehouse.entities.OperationTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
    private final OperationTypeDAO operationTypeDAO;

    @Override
    @Transactional
    public boolean createOperationType(OperationTypeDto operationTypeDto) {
        if (operationTypeDto == null) {
            return false;
        }
        return operationTypeDAO.createOperationType(operationTypeDto.getTypeName());
    }

    @Override
    public OperationTypeDtoRes getOperationTypeById(int id) {
        if (id == 0) {
            return null;
        }
        OperationTypeEntity entity = operationTypeDAO.getOperationTypeById(id);
        OperationTypeDtoRes typeDtoRes = null;
        if (entity != null) {
            typeDtoRes = new OperationTypeDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setTypeId(entity.getTypeId())
                    .setTypeName(entity.getTypeName());
        }
        return typeDtoRes;
    }

    @Override
    public OperationTypeDtoRes getOperationTypeByType(OperationTypeDto operationTypeDto) {
        if (operationTypeDto == null) {
            return null;
        }
        OperationTypeEntity entity = operationTypeDAO.getOperationTypeByType(
                operationTypeDto.getType());
        OperationTypeDtoRes typeDtoRes = null;
        if (entity != null) {
            typeDtoRes = new OperationTypeDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setTypeId(entity.getTypeId())
                    .setTypeName(entity.getTypeName())
                    .setType(entity.getType());
        }
        return typeDtoRes;
    }

    @Override
    public List<OperationTypeDtoRes> getAllOperationType() {
        List<OperationTypeEntity> entityList = operationTypeDAO.getAllOperationType();
        List<OperationTypeDtoRes> dtoResList = new ArrayList<>();

        for (OperationTypeEntity entity : entityList) {
            dtoResList.add(new OperationTypeDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setTypeId(entity.getTypeId())
                    .setTypeName(entity.getTypeName())
                    .setType(entity.getType()));
        }
        return dtoResList;
    }

    @Override
    public boolean deleteOperationTypeById(int id) {
        if (id == 0) {
            return false;
        }
        return operationTypeDAO.deleteOperationTypeById(id);
    }
}
