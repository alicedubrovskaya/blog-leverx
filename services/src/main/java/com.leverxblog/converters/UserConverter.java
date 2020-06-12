package com.leverxblog.converters;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.entity.UserEntity;
import com.leverxblog.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class UserConverter {
    public UserDto convert(UserEntity userEntity) {

        List<ArticleDto> articleDtos=new ArrayList<>();
        userEntity.getArticles().forEach(
                articleEntity->{
                    articleDtos.add(ArticleDto.builder()
                            .id(articleEntity.getId())
                            .title(articleEntity.getTitle())
                            .text(articleEntity.getText())
                            .status(StatusDto.valueOf(articleEntity.getStatus().name()))
                            .createdAt(articleEntity.getCreatedAt())
                            .updatedAt(articleEntity.getUpdatedAt())
                            .build()
                    );
                }
        );
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
                .build();
    }

}
