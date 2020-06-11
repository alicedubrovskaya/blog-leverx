package com.leverxblog.services;

import com.leverxblog.entity.UserEntity;

public interface IUserService {
    void createVerificationToken(UserEntity userEntity, String token);
}
