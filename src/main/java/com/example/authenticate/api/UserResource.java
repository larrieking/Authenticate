package com.example.authenticate.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authenticate.model.Role;
import com.example.authenticate.model.Users;
import com.example.authenticate.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Users user = userService.getUser(username);

                String access_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000)).withIssuer(request.getRequestURL().toString()).withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())).sign(algorithm);
               // String refresh_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000)).withIssuer(request.getRequestURL().toString()).sign(algorithm);
                //response.setHeader("access_token", access_token);
                //response.setHeader("refress_token", refresh_token);

                Map<String, String > tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                //String[]roles = decodedJWT.getClaim("roles").asArray(String.class);

                //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                //SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //filterChain.doFilter(request, response);
            }catch(Exception e){
                //log.error("Error loggin in : {}", e.getMessage());
                response.setHeader("error",  e.getMessage());
                Map<String, String > error = new HashMap<>();
                error.put("access_token", e.getMessage());
                // error.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }


        }
        else{
            throw new  RuntimeException("refreh token is missing");
            //filterChain.doFilter(request,response);
        }

        //userService.addRoleToUser(form.getUsername(), form.getRoleName());
        //return ResponseEntity.ok().build();
    }


}
