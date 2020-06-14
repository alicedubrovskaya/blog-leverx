package com.leverxblog.configurations;

import com.leverxblog.authentification.jwt.JwtTokenProvider;
import com.leverxblog.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String FIND_PUBLIC_ARTICLES = "/articles/public";
    private static final String REGISTER = "/register";
    private static final String RESET = "/register/reset";
    private static final String REGISTRATION_CONFIRMATION = "/register/registrationConfirm";
    private static final String RESET_PASSWORD_CONFIRMATION = "/register/resetPassword";
    private static final String AUTHENTICATE = "/authenticate";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String GET_ALL_USERS = "/users/get";

    private final PasswordEncoder passwordEncoder;
    private final OAuth2ClientContext oAuth2ClientContext;
    private final UserServiceImpl userServiceImpl;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsServiceImpl;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, OAuth2ClientContext oAuth2ClientContext,
                          UserServiceImpl userServiceImpl, JwtTokenProvider jwtTokenProvider,
                          UserDetailsService userDetailsServiceImpl) {
        this.passwordEncoder = passwordEncoder;
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.userServiceImpl = userServiceImpl;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(FIND_PUBLIC_ARTICLES, REGISTER, REGISTRATION_CONFIRMATION
                        , AUTHENTICATE, GET_ALL_USERS, RESET, RESET_PASSWORD_CONFIRMATION).permitAll()
                //    .antMatchers(GET_ALL_USERS).hasRole(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenProvider))
                .and()
                .csrf()
                .disable();
        http.httpBasic().disable();
    }
}

