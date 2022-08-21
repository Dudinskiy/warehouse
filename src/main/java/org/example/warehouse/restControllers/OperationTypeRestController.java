package org.example.warehouse.restControllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.OperationTypeDto;
import org.example.warehouse.dto.OperationTypeDtoRes;
import org.example.warehouse.services.OperationTypeService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/operation-type")
@AllArgsConstructor
public class OperationTypeRestController {

    private final OperationTypeService operationTypeService;

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/create")
    public boolean createOperationType(@RequestBody OperationTypeDto operationTypeDto) {
        return operationTypeService.createOperationType(operationTypeDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-by-id/{id}")
    public OperationTypeDtoRes getOperationTypeById(@PathVariable int id) {
        return operationTypeService.getOperationTypeById(id);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @PostMapping(value = "/get-by-name")
    public OperationTypeDtoRes getOperationTypeByName(@RequestBody OperationTypeDto operationTypeDto) {
        return operationTypeService.getOperationTypeByType(operationTypeDto);
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public List<OperationTypeDtoRes> getAllOperationType(){
        return operationTypeService.getAllOperationType();
    }

    @Secured({"ROLE_Кладовщик", "ROLE_Администратор"})
    @DeleteMapping(value = "/delete/{id}")
    public boolean deleteOperationTypeById(@PathVariable int id) {
        return operationTypeService.deleteOperationTypeById(id);
    }
}
