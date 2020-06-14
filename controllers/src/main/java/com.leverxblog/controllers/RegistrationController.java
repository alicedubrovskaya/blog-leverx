package com.leverxblog.controllers;

import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationTokenEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.services.implementation.UserRegistrationService;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;
    private UserRegistrationService userRegistrationService;
    private ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationController(UserService userService, UserRegistrationService userRegistrationService, ApplicationEventPublisher eventPublisher, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRegistrationService = userRegistrationService;
        this.eventPublisher = eventPublisher;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDto userRegisterDto, WebRequest request) {
        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));

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
        VerificationTokenEntity verificationTokenEntity = userRegistrationService.getVerificationToken(token);
        if (verificationTokenEntity == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        UserEntity userEntity = verificationTokenEntity.getUserEntity();
        Calendar calendar = Calendar.getInstance();
        if ((verificationTokenEntity.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        userEntity.setEnabled(true);
        userEntity.setCreatedAt(new Date(System.currentTimeMillis()));
        userRegistrationService.saveRegisteredUser(userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
