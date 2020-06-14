package com.leverxblog.controllers;

import com.leverxblog.dto.UserDto;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.PasswordResetToken;
import com.leverxblog.entity.security.VerificationTokenEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.event.OnResetPasswordEvent;
import com.leverxblog.exception.UserNotFoundException;
import com.leverxblog.services.implementation.UserRegistrationService;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private static final String SUBJECT = "Reset Password";
    private static final String MESSAGE_SUCCESSFUL_PASSWORD_RESET = "Your password is successfully changed";

    private UserService userService;
    private UserRegistrationService userRegistrationService;
    private ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationController(UserService userService, UserRegistrationService userRegistrationService, ApplicationEventPublisher eventPublisher, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender mailSender) {
        this.userService = userService;
        this.userRegistrationService = userRegistrationService;
        this.eventPublisher = eventPublisher;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailSender = mailSender;
    }

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDto userRegisterDto, WebRequest request) {
        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));

        UserEntity userEntity = userService.addToRegister(userRegisterDto);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        //add try for exception
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntity, appUrl, request.getLocale()));
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

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam("email") String email,
                                              @RequestParam String password,
                                              WebRequest request) throws Exception {
        UserDto userDto = userService.getByEmail(email);
        if (userDto == null) {
            throw new UserNotFoundException(email);
        }

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnResetPasswordEvent(userService.getUserEntity(userDto),
                bCryptPasswordEncoder.encode(password),
                appUrl, request.getLocale()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<Void> confirmResetPassword(@RequestParam("token") String token,
                                                     @RequestParam("password") String password,
                                                     WebRequest request) {

        Locale locale = request.getLocale();
        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        if (passwordResetToken == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        UserEntity userEntity = passwordResetToken.getUserEntity();
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        userEntity.setCreatedAt(new Date(System.currentTimeMillis()));
        userEntity.setPassword(password);
        userRegistrationService.saveRegisteredUser(userEntity);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEntity.getEmail());
        email.setSubject(SUBJECT);
        email.setText(MESSAGE_SUCCESSFUL_PASSWORD_RESET);
        mailSender.send(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
