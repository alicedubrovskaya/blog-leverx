package com.leverxblog.controllers;

import com.leverxblog.authentification.jwt.JwtTokenProvider;
import com.leverxblog.dto.UserAuthDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/authenticate")
public class AuthentificationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public AuthentificationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity authenticate(@RequestBody UserAuthDto userAuthDto){
        try{
            String login = userAuthDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, userAuthDto.getPassword()));
            UserDto userDto=userService.getByLogin(login);

            if (userDto==null){
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }

            String token = jwtTokenProvider.createToken(login);
            Map<Object,Object> response= new HashMap<>();
            response.put("login", userDto.getLogin());
            response.put("firstName", userDto.getFirstName());
            response.put("token",token);

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            throw new SecurityException("Incorrect login or password");
        }
    }


}
