package com.leverxblog.controllers;

import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationToken;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.services.implementation.UserRegistrationService;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;
    private UserRegistrationService userRegistrationService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(UserService userService, UserRegistrationService userRegistrationService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.userRegistrationService = userRegistrationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDto userRegisterDto, WebRequest request) {

        UserEntity userEntity=userService.addToRegister(userRegisterDto);
        if (userEntity==null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        //add try for exception
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntity, appUrl,request.getLocale()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Void> confirmRegistration(@RequestParam("token") String token, WebRequest request) {

        Locale locale = request.getLocale();
        VerificationToken verificationToken = userRegistrationService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        UserEntity userEntity = verificationToken.getUserEntity();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        userEntity.setEnabled(true);
        userRegistrationService.saveRegisteredUser(userEntity);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
