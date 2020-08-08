package com.leverxblog.services.services;

import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService {
    String add(TagDto tagDto);

    List<TagDto> findAll();

    List<ArticleDto> getArticlesByTags(List<String> tagNames);

    Map<String, Integer> amountOfArticlesByTag(TagDto tagDto);
}
