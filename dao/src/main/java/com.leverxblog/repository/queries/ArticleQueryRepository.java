package com.leverxblog.repository.queries;

import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.filtration.impl.ArticleSortProvider;
import com.leverxblog.filtration.Page;

import java.util.List;

public interface ArticleQueryRepository {

    List<ArticleEntity> findAll(Page page, ArticleSortProvider articleSortProvider);
}
