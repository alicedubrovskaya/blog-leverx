package com.leverxblog.services.services;

import com.leverxblog.controllers.exception.InsufficientDataException;
import com.leverxblog.controllers.exception.UserAlreadyExistsException;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.services.dto.UserRegisterDto;
import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.PasswordResetToken;

import java.util.List;

public interface UserService {
    String add(UserDto userDto);

    List<UserDto> getAll();

    Void confirmRegistration(UserEntity userEntity);

    UserEntity register(UserDto userDto) throws InsufficientDataException, UserAlreadyExistsException;

    UserDto getByLogin(String login);

    UserDto getByEmail(String email);

    void createPasswordResetToken(UserEntity userEntity, String token);

    PasswordResetToken getPasswordResetToken(String token);

    UserEntity getUserEntity(UserDto userDto);
}
