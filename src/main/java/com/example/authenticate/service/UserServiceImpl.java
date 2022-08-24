package com.example.authenticate.service;

import com.example.authenticate.Repo.Rolerepo;
import com.example.authenticate.Repo.UserRepo;
import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final Rolerepo rolerepo;
    @Override
    public Users saveUser(Users user) {
        log.info("saving new user to the db");
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("savin new {} role to the db", role.getName());
        return rolerepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding roele {} to user {}", roleName, username);
        Users user = userRepo.findByUsername(username);
        Role role = rolerepo.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public Users getUser(String username) {
       return userRepo.findByUsername(username);
           }

    @Override
    public List<Users> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }
}
