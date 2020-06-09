package com.leverxblog.controllers;

import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        Map id = Collections.singletonMap("id", userService.register(userRegisterDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

}
