package com.leverxblog.controllers.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSendingConfig {

    private final static String MESSAGE_CLASS_PATH = "classpath:messages";
    private final static String MESSAGE_ENCODING = "UTF-8";
    private final static String MAIL_SENDER_HOST = "smtp.gmail.com";
    private final static String MAIL_SENDER_USERNAME = "blogleverx@gmail.com";
    private final static String MAIL_SENDER_PASSWORD = "Blogleverx06";
    private final static String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private final static String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final static String MAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
    private final static String MAIL_DEBUG = "mail.debug";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_CLASS_PATH);
        messageSource.setDefaultEncoding(MESSAGE_ENCODING);
        return messageSource;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_SENDER_HOST);
        mailSender.setPort(587);

        mailSender.setUsername(MAIL_SENDER_USERNAME);
        mailSender.setPassword(MAIL_SENDER_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_TRANSPORT_PROTOCOL, "smtp");
        props.put(MAIL_SMTP_AUTH, "true");
        props.put(MAIL_SMTP_STARTTLS, "true");
        props.put(MAIL_DEBUG, "true");

        return mailSender;
    }

}
