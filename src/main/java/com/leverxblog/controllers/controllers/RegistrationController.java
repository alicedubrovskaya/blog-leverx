package com.leverxblog.controllers.controllers;

import com.leverxblog.services.dto.UserDto;
import com.leverxblog.services.dto.UserRegisterDto;
import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.PasswordResetToken;
import com.leverxblog.dao.entity.security.VerificationTokenEntity;
import com.leverxblog.dao.event.OnRegistrationCompleteEvent;
import com.leverxblog.dao.event.OnResetPasswordEvent;
import com.leverxblog.controllers.exception.UserNotFoundException;
import com.leverxblog.services.services.UserRegistrationService;
import com.leverxblog.services.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private static final String SUBJECT = "Reset Password";
    private static final String MESSAGE_SUCCESSFUL_PASSWORD_RESET = "Your password is successfully changed";

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;


    @PostMapping
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto, WebRequest request) {

        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));

        UserEntity userEntity = userService.addToRegister(userRegisterDto);
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
