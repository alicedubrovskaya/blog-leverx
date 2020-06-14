package com.leverxblog.services.implementation;

import com.leverxblog.converters.UserConverter;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.security.PasswordResetToken;
import com.leverxblog.entity.security.VerificationTokenEntity;
import com.leverxblog.repository.PasswordResetTokenRepository;
import com.leverxblog.repository.UserRepository;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements CrudService<UserDto> {
    private UserConverter userConverter;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public UserServiceImpl(UserConverter userConverter, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public String add(UserDto userDto) {
        UserEntity userEntity = userConverter.convert(userDto);
        return String.valueOf(userRepository.save(userEntity).getId());
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userEntity -> userConverter.convert(userEntity))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getByLogin(String login) {
        UserEntity userEntity = userRepository.findByLogin(login);
        return userConverter.convert(userEntity);
    }

    @Override
    public UserDto getByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userConverter.convert(userEntity);
    }

    public UserEntity getUserEntity(UserDto userDto) {
        return userConverter.convert(userDto);
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByLogin(userRegisterDto.getLogin())) {
            return null;
        }
        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        userRegisterDto.isRegistration();
        String id = add(userRegisterDto);
        return id;
    }

    @Override
    public UserEntity addToRegister(UserDto userDto) {
        UserEntity userEntity = userConverter.convert(userDto);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public void createPasswordResetToken(UserEntity userEntity, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, userEntity);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}

