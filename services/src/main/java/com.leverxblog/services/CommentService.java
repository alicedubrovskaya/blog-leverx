package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.ArticleSortProvider;
import org.springframework.transaction.annotation.Transactional;

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

    List<T> findAll(Integer skip, Integer limit, String sort, String order);

}
