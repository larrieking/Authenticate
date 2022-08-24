package com.example.authenticate.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.authenticate.model.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

}
