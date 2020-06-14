package com.leverxblog.services;

import com.leverxblog.dto.UserDto;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;

import java.util.List;

public interface CrudService<T> {
    String add(T t);

    List<T> getAll();

    UserEntity addToRegister(T t);

    String register(UserRegisterDto userRegisterDto);

    T getByLogin(String login);
}
