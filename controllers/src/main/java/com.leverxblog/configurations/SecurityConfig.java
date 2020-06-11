package com.leverxblog.configurations;

import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final OAuth2ClientContext oAuth2ClientContext;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users","/register","/articles","/comments").permitAll()
                .and()
                .csrf()
                .disable();
        http.httpBasic().disable();
    }

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, OAuth2ClientContext oAuth2ClientContext, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.userService = userService;
    }


}
