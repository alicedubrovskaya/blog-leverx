package com.leverxblog.converters;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter {

    public TagEntity convert(TagDto tagDto) {
        return TagEntity.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    public TagDto convert(TagEntity tagEntity) {
        return TagDto.builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .build();
    }
}
