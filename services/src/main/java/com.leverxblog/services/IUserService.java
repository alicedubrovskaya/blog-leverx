package com.leverxblog.services;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.VerificationTokenEntity;

public interface IUserService {
    void createVerificationToken(UserEntity userEntity, String token);

    VerificationTokenEntity getVerificationToken(String verificationToken);

    void saveRegisteredUser(UserEntity user);
}
