package com.leverxblog.repository;

import com.leverxblog.entity.ArticleEntity;

import java.util.List;
import java.util.UUID;

public interface TagRepositoryQuery {
    int amountOfArticlesByTagId(Long id);

    List<ArticleEntity> findByTagsIds(List<Long> id);
}
