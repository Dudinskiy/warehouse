package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UsersFullDto {
    private int userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String roleName;
    private List<RolesDtoRes> rolesList;
}
