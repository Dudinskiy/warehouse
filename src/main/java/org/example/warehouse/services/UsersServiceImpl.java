package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.RolesDAO;
import org.example.warehouse.dao.UsersDAO;
import org.example.warehouse.dto.UsersFullDto;
import org.example.warehouse.dto.UsersFullDtoRes;
import org.example.warehouse.entities.RolesEntity;
import org.example.warehouse.entities.UsersEntityFull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersDAO usersDAO;
    private final RolesDAO rolesDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean createUser(UsersFullDto usersFullDto) {
        if (usersFullDto == null) {
            System.out.println("usersFullDto == null");
            return false;
        }

        RolesEntity entity = rolesDAO.getRoleByName(usersFullDto.getRole());
        System.out.println("usersFullDto.getRoleName(): " + usersFullDto.getRole());

        if (entity != null) {
            System.out.println(entity.getRoleId());
            System.out.println("password: " + bCryptPasswordEncoder.encode(usersFullDto.getPassword()));

            return usersDAO.createUser(usersFullDto.getFirstName()
                    , usersFullDto.getLastName()
                    , usersFullDto.getLogin()
                    , bCryptPasswordEncoder.encode(usersFullDto.getPassword())
                    , entity.getRoleId());

        } else {
            System.out.println("entity = null");
            return false;
        }
    }

    @Override
    public UsersFullDtoRes getUserByLogin(UsersFullDto usersFullDto) {
        if (usersFullDto == null) {
            return null;
        }
        UsersEntityFull entity = usersDAO.getUserByLogin(usersFullDto.getLogin());
        UsersFullDtoRes user = null;
        if (entity != null) {
            user = new UsersFullDtoRes()
                    .setRowNumber(entity.getRowNumber())
                    .setUserId(entity.getUserId())
                    .setFirstName(entity.getFirstName())
                    .setLastName(entity.getLastName())
                    .setLogin(entity.getLogin())
                    .setRole(entity.getRole());
        }
        return user;
    }

    @Override
    public List<UsersFullDtoRes> getAllUsers() {
        List<UsersFullDtoRes> usersList = new ArrayList<>();
        List<UsersEntityFull> entityList = usersDAO.getAllUsers();

        if (!entityList.isEmpty()) {
            for (UsersEntityFull entity : entityList) {
                UsersFullDtoRes user = new UsersFullDtoRes()
                        .setRowNumber(entity.getRowNumber())
                        .setUserId(entity.getUserId())
                        .setFirstName(entity.getFirstName())
                        .setLastName(entity.getLastName())
                        .setLogin(entity.getLogin())
                        .setRole(entity.getRole());
                usersList.add(user);
            }
        }
        return usersList;
    }

    @Override
    public boolean editUser(UsersFullDto usersFullDto) {
        return false;
    }

    @Override
    public boolean deleteUserByLogin(UsersFullDto usersFullDto) {
        if (usersFullDto == null) {
            return false;
        }
        return usersDAO.deleteUserByLogin(usersFullDto.getLogin());
    }
}
