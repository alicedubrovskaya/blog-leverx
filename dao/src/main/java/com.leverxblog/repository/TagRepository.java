package com.leverxblog.repository;

import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<ArticleEntity> findByNameIn(List<String> tagName);

    TagEntity findByName(String tagName);
}