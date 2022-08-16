package org.example.warehouse.services;

import lombok.AllArgsConstructor;
import org.example.warehouse.dao.UsersDAO;
import org.example.warehouse.entities.UsersEntityFull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersDAO usersDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntityFull user = usersDAO.getUserByLogin(username);
        if (user == null) {
            return null;
        }
        System.out.println("UserDetails loadUserByUsername: " + user.getLogin() + user.getRole());

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
