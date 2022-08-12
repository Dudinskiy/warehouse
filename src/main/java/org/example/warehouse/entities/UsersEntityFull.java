package org.example.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntityFull {
    private int rowNumber;
    private int userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String role;
}
