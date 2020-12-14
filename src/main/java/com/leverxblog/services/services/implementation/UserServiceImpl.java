package com.leverxblog.services.services.implementation;

import com.leverxblog.controllers.exception.InsufficientDataException;
import com.leverxblog.controllers.exception.UserAlreadyExistsException;
import com.leverxblog.services.converters.UserConverter;
import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.dao.entity.security.PasswordResetToken;
import com.leverxblog.dao.repository.PasswordResetTokenRepository;
import com.leverxblog.dao.repository.UserRepository;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.services.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
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

    @Override
    public UserEntity getUserEntity(UserDto userDto) {
        return userConverter.convert(userDto);
    }


    @Override
    public UserEntity register(UserDto userDto) throws InsufficientDataException, UserAlreadyExistsException {
        if (userDto.getLogin() == null || userDto.getPassword() == null || userDto.getEmail()==null) {
            throw new InsufficientDataException();
        }
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new UserAlreadyExistsException(userDto.getLogin());
        }

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = userConverter.convert(userDto);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public Void confirmRegistration(UserEntity userEntity) {
        userEntity.isEnabled();
        userEntity.setCreatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(userEntity);
        return null;
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

