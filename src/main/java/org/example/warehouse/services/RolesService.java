package org.example.warehouse.services;

import org.example.warehouse.dto.RolesDtoRes;

import java.util.List;

public interface RolesService {
    List<RolesDtoRes> getAllRoles();
}
