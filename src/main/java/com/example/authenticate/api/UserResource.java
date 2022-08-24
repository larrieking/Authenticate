package com.example.authenticate.api;


import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;
import com.example.authenticate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserResource {
    private  final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<Users>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/create")
    public ResponseEntity<Users>create(@RequestBody Users users){

        return new ResponseEntity<>(userService.saveUser(users), HttpStatus.CREATED);
    }

    @PostMapping("api/role/save")
    public ResponseEntity<Role>createRole(@RequestBody Role role){
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping("role/addToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}
