package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.RolesDAO;
import org.example.warehouse.dto.RolesDtoRes;
import org.example.warehouse.entities.RolesEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesDAO rolesDAO;

    @Override
    public List<RolesDtoRes> getAllRoles() {
        List<RolesDtoRes> rolesList = new ArrayList<>();
        List<RolesEntity> entityList = rolesDAO.getAllRoles();

        if (!entityList.isEmpty()) {
            for (RolesEntity entity : entityList) {
                rolesList.add(new RolesDtoRes()
                        .setRowNumber(entity.getRowNumber())
                        .setRoleId(entity.getRoleId())
                        .setRoleName(entity.getRoleName())
                        .setRole(entity.getRole()));
            }
        }
        return rolesList;
    }
}
