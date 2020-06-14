package com.leverxblog.repository;

import com.leverxblog.entity.ArticleEntity;

import java.util.List;

public interface TagRepositoryQuery {

    int amountOfArticlesByTagId(Long id);

    List<ArticleEntity> findByTagsIds(List<Long> id);
}
