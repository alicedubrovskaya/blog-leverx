package com.leverxblog.listener;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.services.implementation.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final String SUBJECT = "Registration Confirmation";
    private static final String URL = "/register/registrationConfirm?token=";
    private static final String LOCALGOST = "http://localhost:8080";

    private final UserRegistrationService service;

    private final MessageSource messages;

    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(UserRegistrationService service, MessageSource messages, JavaMailSender mailSender) {
        this.service = service;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEntity userEntity = event.getUserEntity();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(userEntity, token);

        String recipientAddress = userEntity.getEmail();
        String subject = SUBJECT;
        String confirmationUrl = event.getAppUrl() + URL + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(LOCALGOST + confirmationUrl);
        mailSender.send(email);
    }
}
