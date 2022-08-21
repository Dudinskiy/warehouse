package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RolesDtoRes {
    private int rowNumber;
    private int roleId;
    private String roleName;
    private String role;
}
