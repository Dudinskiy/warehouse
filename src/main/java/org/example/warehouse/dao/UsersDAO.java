package org.example.warehouse.dao;

import org.example.warehouse.entities.UsersEntityFull;

import java.util.List;

public interface UsersDAO {

    boolean createUser(String firstName, String lastName
            , String login, String password, int roleId);

    UsersEntityFull getUserByLogin(String login);

    List<UsersEntityFull> getAllUsers();

    boolean editUser(String login);

    boolean deleteUserByLogin(String login);
}
