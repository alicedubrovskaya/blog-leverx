package com.leverxblog.listener;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.event.OnResetPasswordEvent;
import com.leverxblog.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {
    private static final String SUBJECT = "Reset Password";
    private static final String URL = "/register/resetPassword?token=";
    private static final String URL_END = "&password=";
    private static final String LOCALGOST = "http://localhost:8080";

    private final UserServiceImpl service;

    private final JavaMailSender mailSender;

    @Autowired
    public ResetPasswordListener(UserServiceImpl service, JavaMailSender mailSender) {
        this.service = service;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(OnResetPasswordEvent event) {
        UserEntity userEntity = event.getUserEntity();
        String token = UUID.randomUUID().toString();
        service.createPasswordResetToken(userEntity, token);

        String recipientAddress = userEntity.getEmail();
        String subject = SUBJECT;
        String confirmationUrl = event.getAppUrl() + URL + token + URL_END + event.getPassword();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(LOCALGOST + confirmationUrl);
        mailSender.send(email);
    }

}
