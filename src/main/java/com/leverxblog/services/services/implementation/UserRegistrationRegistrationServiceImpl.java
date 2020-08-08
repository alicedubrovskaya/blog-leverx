package com.leverxblog.services.services.implementation;

import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.VerificationTokenEntity;
import com.leverxblog.dao.repository.UserRepository;
import com.leverxblog.dao.repository.VerificationTokenRepository;
import com.leverxblog.services.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserRegistrationRegistrationServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void createVerificationToken(UserEntity userEntity, String token) {
        VerificationTokenEntity myToken = new VerificationTokenEntity(token, userEntity);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public void saveRegisteredUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public VerificationTokenEntity getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }
}
