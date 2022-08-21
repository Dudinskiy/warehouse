package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class OperationReportDtoRes {
    private String typeName;
    private int operationAmount;
    private int productAmount;
    private BigDecimal totalCost;
    private List<OperationReportDtoRes> reportDtoResList;
    private LocalDate start;
    private LocalDate end;
}
