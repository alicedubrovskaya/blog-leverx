package com.leverxblog.services;

import com.leverxblog.dto.UserDto;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.PasswordResetToken;

import java.util.List;

public interface CrudService<T> {
    String add(T t);

    List<T> getAll();

    UserEntity addToRegister(T t);

    String register(UserRegisterDto userRegisterDto);

    T getByLogin(String login);

    T getByEmail(String email);

    void createPasswordResetToken(UserEntity userEntity, String token);

    PasswordResetToken getPasswordResetToken(String token);
}
