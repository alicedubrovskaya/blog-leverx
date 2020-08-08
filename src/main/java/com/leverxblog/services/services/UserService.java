package com.leverxblog.services.services;

import com.leverxblog.services.dto.UserDto;
import com.leverxblog.services.dto.UserRegisterDto;
import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.PasswordResetToken;

import java.util.List;

public interface UserService {
    String add(UserDto userDto);

    List<UserDto> getAll();

    UserEntity addToRegister(UserDto userEntity);

    String register(UserRegisterDto userRegisterDto);

    UserDto getByLogin(String login);

    UserDto getByEmail(String email);

    void createPasswordResetToken(UserEntity userEntity, String token);

    PasswordResetToken getPasswordResetToken(String token);

    UserEntity getUserEntity(UserDto userDto);
}
