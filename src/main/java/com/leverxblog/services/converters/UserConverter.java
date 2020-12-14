package com.leverxblog.services.converters;

import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.StatusDto;
import com.leverxblog.dao.entity.UserEntity;
import com.leverxblog.services.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto convert(UserEntity userEntity) {

        List<ArticleDto> articleDtos = userEntity.getArticles().stream()
                .map(articleEntity -> ArticleDto.builder()
                        .id(articleEntity.getId())
                        .title(articleEntity.getTitle())
                        .text(articleEntity.getText())
                        .status(StatusDto.valueOf(articleEntity.getStatus().name()))
                        .createdAt(articleEntity.getCreatedAt())
                        .updatedAt(articleEntity.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .createdAt(userEntity.getCreatedAt())
                .articles(articleDtos)
                .login(userEntity.getLogin())
                .role(userEntity.getRole())
                .build();
    }

    public UserEntity convert(UserDto userDto) {

        return UserEntity.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .createdAt(userDto.getCreatedAt())
                .login(userDto.getLogin())
                .role(userDto.getRole())
                .enabled(userDto.isEnabled())
                .verificationTokenEntity(userDto.getVerificationTokenEntity())
                .build();
    }

}
