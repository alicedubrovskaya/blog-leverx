package com.leverxblog.listener;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.event.OnRegistrationCompleteEvent;
import com.leverxblog.services.implementation.UserRegistrationService;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserRegistrationService service;

    private  final MessageSource messages;

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

    private void confirmRegistration(OnRegistrationCompleteEvent event){
        UserEntity userEntity=event.getUserEntity();
        String token= UUID.randomUUID().toString();
        service.createVerificationToken(userEntity,token);

        String recipientAddress= userEntity.getEmail();
        String subject="Registration Confirmation";
        String confirmationUrl= event.getAppUrl() +"/registrationConfirm.html?token"+token;
       // String message= messages.getMessage("message.regSucc",null,event.getLocale());

        SimpleMailMessage email= new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
     //   email.setText(message+"rn"+"http://localhost:8080"+confirmationUrl);
        email.setText("http://localhost:8080"+confirmationUrl);
        mailSender.send(email);
    }
}
