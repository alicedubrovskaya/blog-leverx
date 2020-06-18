package com.leverxblog.repository;

import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.Status;
import com.leverxblog.entity.TagEntity;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.ArticleSortProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> getByUserEntity_id(UUID userId);

    List<ArticleEntity> getByStatus(Status status);

    ArticleEntity getByTitle(String string);

    List<ArticleEntity> findAll(Page page, ArticleSortProvider articleSortProvider);

}
