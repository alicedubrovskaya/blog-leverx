package com.leverxblog.services.services;

import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.VerificationTokenEntity;

public interface UserRegistrationService {
    void createVerificationToken(UserEntity userEntity, String token);

    VerificationTokenEntity getVerificationToken(String verificationToken);

    void saveRegisteredUser(UserEntity user);
}
