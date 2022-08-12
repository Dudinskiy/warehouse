package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationInfoEntity;

public interface OperationInfoDAO {
    boolean createOperationInfo(int operationId, int productId, int productAmount);

    OperationInfoEntity getOperationInfoByOperationId(int id);

    OperationInfoEntity getOperationInfoByProductId(int id);

    OperationInfoEntity getOperationInfoByOperationIdAndProductId(int operId, int prodId);

    boolean deleteOperationInfoByOperationId(int id);
}
