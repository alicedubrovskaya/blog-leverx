package com.leverxblog.dao.repository.queries;

import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.services.filtration.impl.ArticleSortProvider;
import com.leverxblog.services.filtration.Page;

import java.util.List;

public interface ArticleQueryRepository {

    List<ArticleEntity> findAll(Page page, ArticleSortProvider articleSortProvider);
}
