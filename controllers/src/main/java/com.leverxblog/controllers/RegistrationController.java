package com.leverxblog.controllers;

import com.leverxblog.dto.UserDto;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.PasswordResetToken;
import com.leverxblog.entity.security.VerificationTokenEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.event.OnResetPasswordEvent;
import com.leverxblog.exception.UserNotFoundException;
import com.leverxblog.services.implementation.UserRegistrationServiceImpl;
import com.leverxblog.services.implementation.UserServiceImpl;
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

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private static final String SUBJECT = "Reset Password";
    private static final String MESSAGE_SUCCESSFUL_PASSWORD_RESET = "Your password is successfully changed";

    private final UserServiceImpl userServiceImpl;
    private final UserRegistrationServiceImpl userRegistrationServiceImpl;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl, UserRegistrationServiceImpl userRegistrationServiceImpl,
                                  ApplicationEventPublisher eventPublisher, BCryptPasswordEncoder bCryptPasswordEncoder,
                                  JavaMailSender mailSender) {
        this.userServiceImpl = userServiceImpl;
        this.userRegistrationServiceImpl = userRegistrationServiceImpl;
        this.eventPublisher = eventPublisher;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailSender = mailSender;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserRegisterDto userRegisterDto, WebRequest request) {
        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));

        UserEntity userEntity = userServiceImpl.addToRegister(userRegisterDto);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntity, appUrl, request.getLocale()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<Void> confirmRegistration(@RequestParam("token") String token, WebRequest request) {

        Locale locale = request.getLocale();
        VerificationTokenEntity verificationTokenEntity = userRegistrationServiceImpl.getVerificationToken(token);
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
        userRegistrationServiceImpl.saveRegisteredUser(userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam("email") String email,
                                              @RequestParam String password,
                                              WebRequest request) throws Exception {
        UserDto userDto = userServiceImpl.getByEmail(email);
        if (userDto == null) {
            throw new UserNotFoundException(email);
        }

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnResetPasswordEvent(userServiceImpl.getUserEntity(userDto),
                bCryptPasswordEncoder.encode(password),
                appUrl, request.getLocale()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<Void> confirmResetPassword(@RequestParam("token") String token,
                                                     @RequestParam("password") String password,
                                                     WebRequest request) {

        Locale locale = request.getLocale();
        PasswordResetToken passwordResetToken = userServiceImpl.getPasswordResetToken(token);
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
        userRegistrationServiceImpl.saveRegisteredUser(userEntity);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEntity.getEmail());
        email.setSubject(SUBJECT);
        email.setText(MESSAGE_SUCCESSFUL_PASSWORD_RESET);
        mailSender.send(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
