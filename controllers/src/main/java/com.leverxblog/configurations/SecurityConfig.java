package com.leverxblog.configurations;

import com.leverxblog.authentification.jwt.JwtTokenProvider;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final PasswordEncoder passwordEncoder;
    private final OAuth2ClientContext oAuth2ClientContext;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsServiceImpl;


    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, OAuth2ClientContext oAuth2ClientContext,
                          UserService userService, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsServiceImpl) {
        this.passwordEncoder = passwordEncoder;
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.userService = userService;
        this.jwtTokenProvider=jwtTokenProvider;
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
                .antMatchers("/users/get","/register","/register/registrationConfirm", "/authentificate").permitAll()
                .antMatchers("/articles/addTag").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenProvider))
                .and()
                .csrf()
                .disable();
        http.httpBasic().disable();
    }
}

