package com.leverxblog.dao.repository.queries;

import com.leverxblog.dao.entity.ArticleEntity;

import java.util.List;

public interface TagQueryRepository {

    int amountOfArticlesByTagId(Long id);

    List<ArticleEntity> findByTagsIds(List<Long> id);
}
