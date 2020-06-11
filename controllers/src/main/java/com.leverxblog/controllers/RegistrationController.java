package com.leverxblog.controllers;

import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDto userRegisterDto, WebRequest request) {
       // Map id = Collections.singletonMap("id", userService.register(userRegisterDto));
        //return new ResponseEntity<>(id, HttpStatus.CREATED);

        UserEntity userEntity=userService.addToRegister(userRegisterDto);
        if (userEntity==null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        //add try for exception
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntity, appUrl,request.getLocale()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
