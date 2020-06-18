package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.entity.UserEntity;
import javassist.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    String add(ArticleDto articleDto, UUID userId);

    List<ArticleDto> getAll();

    ArticleDto getById(UUID id) throws NotFoundException;

    void delete(UUID id);

    List<ArticleDto> getByUserId(UUID userId);

    List<ArticleDto> getByPublicStatus();

    List<ArticleDto> findAll(Integer skip, Integer limit, String sort, String order);

    ArticleDto getByTitle(String title);
}
