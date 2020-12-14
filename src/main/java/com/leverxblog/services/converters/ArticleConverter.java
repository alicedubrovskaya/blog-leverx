package com.leverxblog.services.converters;

import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.dao.entity.Status;
import com.leverxblog.dao.entity.TagEntity;
import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.StatusDto;
import com.leverxblog.services.dto.TagDto;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleConverter {

    public ArticleDto convert(ArticleEntity articleEntity) {

        List<TagDto> tagDtos = articleEntity.getTags().stream()
                .map(tagEntity -> TagDto.builder()
                        .id(tagEntity.getId())
                        .name(tagEntity.getName())
                        .build())
                .collect(Collectors.toList());

        ArticleDto articleDto = ArticleDto.builder()
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
                                .build()
                )
                .build();
        articleDto.setTags(tagDtos);
        return articleDto;
    }

    public ArticleEntity convert(ArticleDto articleDto) {

        List<TagEntity> tagEntities = articleDto.getTags().stream()
                .map(tagDto -> TagEntity.builder()
                        .id(tagDto.getId())
                        .name(tagDto.getName())
                        .build())
                .collect(Collectors.toList());

        ArticleEntity articleEntity = ArticleEntity.builder()
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
                .tags(tagEntities)
                .build();

        return articleEntity;
    }
}
