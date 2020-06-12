package com.leverxblog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String login;
    private Date createdAt;
    private String email;
    private Role role;
    private List<ArticleDto> articles=new ArrayList<>();

    @JsonIgnore
    public UserDto getUser() {
        return this;
    }

    @JsonIgnore
    public void addArticle(ArticleDto articleDto) {
        this.articles.add(articleDto);
        articleDto.setUserDto(this);
    }

}
