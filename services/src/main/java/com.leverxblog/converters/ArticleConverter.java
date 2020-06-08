package com.leverxblog.converters;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.entity.*;
import com.leverxblog.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverter {
    public ArticleDto convert(ArticleEntity articleEntity){
        return ArticleDto.builder()
                .id(articleEntity.getId())
                .title(articleEntity.getTitle())
                .text(articleEntity.getText())
                .createdAt(articleEntity.getCreatedAt())
                .updatedAt(articleEntity.getUpdatedAt())
                .userEntity_id(articleEntity.getUserEntity().getId())
                .status(StatusDto.valueOf(articleEntity.getStatus().name()))
                .userDto(
                        UserDto.builder()
                        .id(articleEntity.getUserEntity().getId())
                        .id(articleEntity.getUserEntity().getId())
                        .firstName(articleEntity.getUserEntity().getFirstName())
                        .lastName(articleEntity.getUserEntity().getLastName())
                        .password(articleEntity.getUserEntity().getPassword())
                        .email(articleEntity.getUserEntity().getEmail())
                        .createdAt(articleEntity.getUserEntity().getCreatedAt())
                       //    .articles()
                        .build()
                )
                .build();
    }

    public ArticleEntity convert(ArticleDto articleDto){
        ArticleEntity articleEntity=ArticleEntity.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .text(articleDto.getText())
                .createdAt(articleDto.getCreatedAt())
                .updatedAt(articleDto.getUpdatedAt())
                .status(Status.valueOf(articleDto.getStatus().name()))
                .userEntity(
                        UserEntity.builder()
                            .id(articleDto.getUserEntity_id())
                            .build()
                )
                .build();

        return articleEntity;
    }
}
