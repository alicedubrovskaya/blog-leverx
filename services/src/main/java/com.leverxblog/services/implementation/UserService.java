package com.leverxblog.services.implementation;

import com.leverxblog.converters.UserConverter;
import com.leverxblog.dto.UserRegisterDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.repository.UserRepository;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements CrudService<UserDto> {
    private UserConverter userConverter;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserConverter userConverter, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @Override
    public String add(UserDto userDto) {
        return String.valueOf(userRepository.save(userConverter.convert(userDto)).getId());
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userEntity -> userConverter.convert(userEntity))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(UUID id) throws Exception {
        return null;
    }

    public String register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByLogin(userRegisterDto.getLogin())) {
            return null;
        }
        userRegisterDto.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        userRegisterDto.isRegistration();
        String id=add(userRegisterDto);
        return id;
    }

    public UserEntity addToRegister(UserDto userDto) {
        UserEntity userEntity=userConverter.convert(userDto);
        userRepository.save(userEntity);
        return userEntity;
    }

}

