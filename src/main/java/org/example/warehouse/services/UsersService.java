package org.example.warehouse.services;

import org.example.warehouse.dto.UsersFullDto;
import org.example.warehouse.dto.UsersFullDtoRes;

import java.util.List;

public interface UsersService {
    boolean createUser(UsersFullDto usersFullDto);

    UsersFullDtoRes getUserByLogin(UsersFullDto usersFullDto);

    List<UsersFullDtoRes> getAllUsers();

    boolean editUser(UsersFullDto usersFullDto);

    boolean deleteUserByLogin(UsersFullDto usersFullDto);
}
