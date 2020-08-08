package com.leverxblog.services.services;

import com.leverxblog.services.dto.CommentDto;
import javassist.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    String add(CommentDto commentDto);

    CommentDto getById(UUID id) throws NotFoundException;

    List<CommentDto> getAll();

    void delete(UUID id);

    List<CommentDto> getByArticleId(UUID articleId);

    CommentDto getByArticleAndCommentId(UUID articleId, UUID commentId);

    List<CommentDto> findAll(Integer skip, Integer limit, String sort, String order);

}
