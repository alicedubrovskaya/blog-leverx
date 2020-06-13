package com.leverxblog.services.implementation;

import com.leverxblog.converters.CommentConverter;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;
import com.leverxblog.exception.CommentNotFoundException;
import com.leverxblog.repository.CommentRepository;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService implements CrudService<CommentDto> {
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public String add(CommentDto commentDto) {
        commentDto.setCreatedAt(new Date(System.currentTimeMillis()));
        return String.valueOf(commentRepository.save(commentConverter.convert(commentDto)).getId());
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(commentEntity -> commentConverter.convert(commentEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(UUID id) throws Exception {
        return commentConverter.convert(commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)));
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

    public List<CommentDto> getByArticleId(UUID articleId) {
        return commentRepository.findByArticleEntityId(articleId).stream()
                .map(commentEntity -> commentConverter.convert(commentEntity))
                .collect(Collectors.toList());
    }

    public CommentDto getByArticleAndCommentId(UUID articleId, UUID commentId) {
        CommentEntity commentEntity = commentRepository.findByIdAndArticleEntityId(commentId, articleId);
        return commentConverter.convert(commentRepository.findByIdAndArticleEntityId(commentId, articleId));
    }

}
