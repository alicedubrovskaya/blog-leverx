package com.leverxblog.services;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationToken;

public interface IUserService {
    void createVerificationToken(UserEntity userEntity, String token);
    VerificationToken getVerificationToken(String verificationToken);
    void saveRegisteredUser(UserEntity user);
}
