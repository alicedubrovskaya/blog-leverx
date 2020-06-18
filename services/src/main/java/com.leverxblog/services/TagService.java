package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService {
    String add(TagDto tagDto);

    List<TagDto> findAll();

    List<ArticleDto> getArticlesByTags(List<String> tagNames);

    Map<String, Integer> amountOfArticlesByTag(TagDto tagDto);
}
