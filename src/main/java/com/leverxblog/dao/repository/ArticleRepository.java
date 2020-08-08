package com.leverxblog.dao.repository;

import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.dao.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> getByUserEntity_id(UUID userId);

    List<ArticleEntity> getByStatus(Status status);

    ArticleEntity getByTitle(String string);

}
