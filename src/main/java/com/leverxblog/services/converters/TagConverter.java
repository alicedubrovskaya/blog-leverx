package com.leverxblog.services.converters;

import com.leverxblog.services.dto.TagDto;
import com.leverxblog.dao.entity.TagEntity;
import org.springframework.stereotype.Component;

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
