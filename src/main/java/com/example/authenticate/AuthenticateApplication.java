package com.example.authenticate;

import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;
import com.example.authenticate.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AuthenticateApplication {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }




    public static void main(String[] args) {
        SpringApplication.run(AuthenticateApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role("Role_User"));
            userService.saveRole(new Role("Role_Manager"));
            userService.saveRole(new Role("Role_Admin"));
            userService.saveRole(new Role("Role_Super_User"));

            userService.saveUser(new Users(null, "John Troval", "John", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johnu", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johna", "1234", new ArrayList<>()));
            userService.saveUser(new Users(null, "John Troval", "Johni", "1234", new ArrayList<>()));

            userService.addRoleToUser("john", "Role_User");
            userService.addRoleToUser("johnu", "Role_Manager");
            userService.addRoleToUser("johna", "Role_Admin");
            userService.addRoleToUser("johni", "Role_Super_User");
        };
    }
}
