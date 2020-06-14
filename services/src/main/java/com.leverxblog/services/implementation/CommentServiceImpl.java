package com.leverxblog.services.implementation;

import com.leverxblog.converters.CommentConverter;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.entity.CommentEntity;
import com.leverxblog.exception.CommentNotFoundException;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.CommentSortProvider;
import com.leverxblog.repository.CommentRepository;
import com.leverxblog.repository.queries.CommentQueryRepository;
import com.leverxblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService<CommentDto> {
    private CommentRepository commentRepository;
    private CommentQueryRepository commentQueryRepository;
    private CommentConverter commentConverter;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentQueryRepository commentQueryRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentQueryRepository = commentQueryRepository;
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

    @Transactional
    @Override
    public List<CommentDto> findAll(Integer skip, Integer limit, String sort, String order) {
        return commentQueryRepository
                .findAll(new Page(skip, limit), new CommentSortProvider(sort, order))
                .stream()
                .map(articleEntity ->  commentConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }
}
