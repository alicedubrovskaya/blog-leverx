package com.leverxblog.services.implementation;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.dto.ArticleDto;
import com.leverxblog.exception.ArticleNotFoundException;
import com.leverxblog.repository.ArticleRepository;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService implements CrudService<ArticleDto> {
    private ArticleConverter articleConverter;
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleConverter articleConverter, ArticleRepository articleRepository) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
    }

    @Override
    public String add(ArticleDto articleDto) {
        return String.valueOf(articleRepository.save(articleConverter.convert(articleDto)).getId());
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleRepository.findAll().stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto getById(UUID id) throws Exception {
        return articleConverter.convert(articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id)));
    }

    @Override
    public void delete(UUID id) {
        articleRepository.deleteById(id);
    }


}
