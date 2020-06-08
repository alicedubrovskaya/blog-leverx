package com.leverxblog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverxblog.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private UUID id;
    private UUID userEntity_id;
    private String title;
    private String text;
    private Date createdAt;
    private Date updatedAt;
    private UserDto userDto;
    private StatusDto status;

    @JsonIgnore
    public ArticleDto getArticle() {
        return this;
    }

}
