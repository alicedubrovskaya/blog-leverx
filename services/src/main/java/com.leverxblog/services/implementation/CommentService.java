package com.leverxblog.services.implementation;

import com.leverxblog.converters.CommentConverter;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.repository.CommentRepository;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}
