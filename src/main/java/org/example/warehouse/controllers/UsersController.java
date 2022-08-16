package org.example.warehouse.controllers;

import lombok.AllArgsConstructor;
import org.example.warehouse.dto.RolesDtoRes;
import org.example.warehouse.dto.UsersFullDto;
import org.example.warehouse.dto.UsersFullDtoRes;
import org.example.warehouse.services.RolesService;
import org.example.warehouse.services.UsersService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UsersController {

    private final UsersService usersService;
    private final RolesService rolesService;

    @Secured({"ROLE_Администратор"})
    @GetMapping(value = "/add")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        UsersFullDto usersFullDto = new UsersFullDto();
        List<RolesDtoRes> rolesList = rolesService.getAllRoles();
        usersFullDto.setRolesList(rolesList);
        modelAndView.addObject("usersFullDto", usersFullDto);
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @PostMapping(value = "/create")
    public ModelAndView createUser(@ModelAttribute UsersFullDto usersFullDto) {
        ModelAndView modelAndView = new ModelAndView("addUserRes");
        String response;
        if (usersService.createUser(usersFullDto)) {
            response = "Пользователь добавлен в базу данных";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @GetMapping(value = "/get-by-login-form")
    public ModelAndView getUserByLoginForm() {
        ModelAndView modelAndView = new ModelAndView("getUserByLoginForm");
        modelAndView.addObject("usersFullDto", new UsersFullDto());
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @PostMapping(value = "/get-by-login")
    public ModelAndView getUserByLogin(@ModelAttribute UsersFullDto usersFullDto) {
        ModelAndView modelAndView = new ModelAndView("getUserByLogin");
        UsersFullDtoRes usersFullDtoRes = usersService.getUserByLogin(usersFullDto);
        modelAndView.addObject("usersFullDtoRes", usersFullDtoRes);
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @GetMapping(value = "/get-all")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("getAllUsers");
        List<UsersFullDtoRes> usersFullDtoResList = usersService.getAllUsers();
        modelAndView.addObject("usersFullDtoResList", usersFullDtoResList);
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @GetMapping(value = "/delete-by-login-form")
    public ModelAndView deleteUserByLoginForm() {
        ModelAndView modelAndView = new ModelAndView("deleteUserByLoginForm");
        modelAndView.addObject("usersFullDto", new UsersFullDto());
        return modelAndView;
    }

    @Secured({"ROLE_Администратор"})
    @PostMapping(value = "/delete-by-login")
    public ModelAndView deleteUserByLogin(@ModelAttribute UsersFullDto usersFullDto) {
        ModelAndView modelAndView = new ModelAndView("deleteUserByLoginRes");
        String response;
        if (usersService.deleteUserByLogin(usersFullDto)) {
            response = "Пользователь удален";
        } else {
            response = "Произошел сбой. Попробуйте снова";
        }
        modelAndView.addObject("response", response);
        return modelAndView;
    }
}
