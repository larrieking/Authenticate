package com.example.authenticate.Repo;

import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolerepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
