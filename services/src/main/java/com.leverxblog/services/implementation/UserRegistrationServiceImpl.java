package com.leverxblog.services.implementation;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.VerificationTokenEntity;
import com.leverxblog.repository.UserRepository;
import com.leverxblog.repository.VerificationTokenRepository;
import com.leverxblog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements IUserService {
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
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
