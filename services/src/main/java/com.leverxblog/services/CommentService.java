package com.leverxblog.services;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.ArticleSortProvider;
import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CommentService {
    String add(CommentDto commentDto);

    CommentDto getById(UUID id) throws NotFoundException;

    List<CommentDto> getAll();

    void delete(UUID id);

    List<CommentDto> getByArticleId(UUID articleId);

    CommentDto getByArticleAndCommentId(UUID articleId, UUID commentId);

    List<CommentDto> findAll(Integer skip, Integer limit, String sort, String order);

}
