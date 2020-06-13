package com.leverxblog.converters;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.entity.*;
import com.leverxblog.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleConverter {
    public ArticleDto convert(ArticleEntity articleEntity){
        List<TagDto> tagDtos=new ArrayList<>();
        articleEntity.getTags().forEach(
                tagEntity -> tagDtos.add(
                       TagDto.builder()
                               .id(tagEntity.getId())
                               .name(tagEntity.getName())
                               .build()
                )
        );
        ArticleDto articleDto= ArticleDto.builder()
                .id(articleEntity.getId())
                .title(articleEntity.getTitle())
                .text(articleEntity.getText())
                .createdAt(articleEntity.getCreatedAt())
                .updatedAt(articleEntity.getUpdatedAt())
                .userEntity_id(articleEntity.getUserEntity().getId())
                .status(StatusDto.valueOf(articleEntity.getStatus().name()))
          //      .tags(tagDtos)
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
        articleDto.setTags(tagDtos);
        return articleDto;
    }

    public ArticleEntity convert(ArticleDto articleDto){
      List<TagEntity> tagEntities=new ArrayList<>();
        for(TagDto tagDto: articleDto.getTags()){
            if (tagDto.getId()==null){
                tagEntities.add(TagEntity.builder()
                        .name(tagDto.getName())
                        .build()
                );
            }
            else {
                tagEntities.add(TagEntity.builder()
                        .id(tagDto.getId())
                        .build()
                );
            }
        }
     /*   articleDto.getTags().forEach(
                tagDto->{
                    tagEntities.add(TagEntity.builder()
                            .id(tagDto.getId())
                            .name(tagDto.getName())
                            .build()
                    );
                }
        );

*/
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
               .tags(tagEntities)
               .build();

        return articleEntity;
    }
}
