package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService<T> {
    String add(T t);

    List<T> findAll();

    List<ArticleDto> getArticlesByTags(List<String> tagNames);

    Map<String, Integer> amountOfArticlesByTag(T t);
}
