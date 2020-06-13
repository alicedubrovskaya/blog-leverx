package com.leverxblog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CommentDto {
    private UUID id;
    private String message;
    private Date createdAt;
    private UserDto userDto;
    private ArticleDto articleDto;
    private UUID userId;
    private UUID articleId;
}
