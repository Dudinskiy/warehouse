package org.example.warehouse.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UsersFullDtoRes {
    private int rowNumber;
    private int userId;
    private String firstName;
    private String lastName;
    private String login;
    private String role;
}
