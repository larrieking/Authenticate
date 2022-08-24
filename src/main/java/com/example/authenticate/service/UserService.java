package com.example.authenticate.service;

import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;

import java.util.List;


public interface UserService {
    Users saveUser(Users user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    Users getUser(String username);
    List<Users> getUsers();

}
