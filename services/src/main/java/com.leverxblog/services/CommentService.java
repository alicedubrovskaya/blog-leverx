package com.leverxblog.services;

import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CommentService<T> {
    String add(T t);

    T getById(UUID id) throws Exception;

    List<T> getAll();

    void delete(UUID id);

    List<T> getByArticleId(UUID articleId);

    T getByArticleAndCommentId(UUID articleId, UUID commentId);
}
