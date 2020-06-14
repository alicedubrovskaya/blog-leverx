package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;

import java.util.List;
import java.util.UUID;

public interface ArticleService<T> {
    String add(T t, UUID userId);

    List<T> getAll();

    T getById(UUID id) throws Exception;

    void delete(UUID id);

    List <T> getByUserId(UUID userId);

    List <T> getByPublicStatus();

    List<T> findAll(Integer skip, Integer limit, String sort, String order);
}
