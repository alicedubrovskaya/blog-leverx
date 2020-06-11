package com.leverxblog.services.implementation;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationToken;
import com.leverxblog.repository.UserRepository;
import com.leverxblog.repository.VerificationTokenRepository;
import com.leverxblog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class UserRegistrationService implements IUserService {
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void createVerificationToken(UserEntity userEntity, String token) {
        VerificationToken myToken= new VerificationToken(token,userEntity);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public void saveRegisteredUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }
}
