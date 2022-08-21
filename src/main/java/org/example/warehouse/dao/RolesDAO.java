package org.example.warehouse.dao;

import org.example.warehouse.entities.RolesEntity;

import java.util.List;

public interface RolesDAO {
    RolesEntity getRoleByName(String role);

    List<RolesEntity> getAllRoles();
}
