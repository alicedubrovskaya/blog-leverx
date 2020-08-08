package com.leverxblog.services.converters;

import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.CommentDto;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.dao.entity.CommentEntity;
import com.leverxblog.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentDto convert(CommentEntity commentEntity) {

        return CommentDto.builder()
                .id(commentEntity.getId())
                .message(commentEntity.getMessage())
                .createdAt(commentEntity.getCreatedAt())
                .userId(commentEntity.getUserEntity().getId())
                .articleId(commentEntity.getArticleEntity().getId())
                .userDto(UserDto.builder()
                        .id(commentEntity.getUserEntity().getId())
                        .firstName(commentEntity.getUserEntity().getFirstName())
                        .lastName(commentEntity.getUserEntity().getLastName())
                        .build())
                .articleDto(ArticleDto.builder()
                        .id(commentEntity.getArticleEntity().getId())
                        .title(commentEntity.getArticleEntity().getTitle())
                        .build()
                )
                .build();
    }

    public CommentEntity convert(CommentDto commentDto) {
        return CommentEntity.builder()
                .id(commentDto.getId())
                .message(commentDto.getMessage())
                .createdAt(commentDto.getCreatedAt())
                .userEntity(
                        UserEntity.builder()
                                .id(commentDto.getUserId())
                                .build()
                )
                .articleEntity(
                        ArticleEntity.builder()
                                .id(commentDto.getArticleId())
                                .build()
                )
                .build();
    }
}
