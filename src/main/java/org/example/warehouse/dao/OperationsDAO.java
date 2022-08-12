package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationsEntity;
import org.example.warehouse.entities.OperationsEntityFull;

import java.time.LocalDate;
import java.util.List;


public interface OperationsDAO {
    boolean createOperation(int typeId, String invoice);

    OperationsEntity getOperationById(int id);

    OperationsEntity getOperationByInvoice(String invoice);

    List<OperationsEntityFull> getOperationByInvoiceFull(String invoice);

    List<OperationsEntityFull> getAllOperationByPeriodFull(LocalDate start, LocalDate end);

    List<OperationsEntityFull> getAllOperationByPeriodAndTypeFull(LocalDate start, LocalDate end, String type);

    boolean deleteOperationById(int id);
}
