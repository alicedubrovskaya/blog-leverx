package com.leverxblog.repository;

import com.leverxblog.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    List<CommentEntity> findByArticleEntityId(UUID articleId);

    CommentEntity findByIdAndArticleEntityId(UUID commentId, UUID articleId);
}
