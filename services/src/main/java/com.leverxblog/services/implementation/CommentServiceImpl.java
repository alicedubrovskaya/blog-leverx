package com.leverxblog.services.implementation;

import com.leverxblog.converters.CommentConverter;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;
import com.leverxblog.exception.CommentNotFoundException;
import com.leverxblog.repository.CommentRepository;
import com.leverxblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService<CommentDto> {
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public String add(CommentDto commentDto) {
        commentDto.setCreatedAt(new Date(System.currentTimeMillis()));
        CommentEntity commentEntity = commentConverter.convert(commentDto);
        return String.valueOf(commentRepository.save(commentEntity).getId());
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(commentEntity -> commentConverter.convert(commentEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(UUID id) throws Exception {
        CommentEntity commentEntity =commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        return commentConverter.convert(commentEntity);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> getByArticleId(UUID articleId) {
        return commentRepository.findByArticleEntityId(articleId).stream()
                .map(commentEntity -> commentConverter.convert(commentEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getByArticleAndCommentId(UUID articleId, UUID commentId) {
        CommentEntity commentEntity = commentRepository.findByIdAndArticleEntityId(commentId, articleId);
        return commentConverter.convert(commentEntity);
    }
}
