package com.leverxblog.services.services.implementation;

import com.leverxblog.services.converters.ArticleConverter;
import com.leverxblog.services.converters.TagConverter;
import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.TagDto;
import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.dao.entity.Status;
import com.leverxblog.dao.entity.TagEntity;
import com.leverxblog.controllers.exception.ArticleNotFoundException;
import com.leverxblog.services.filtration.impl.ArticleSortProvider;
import com.leverxblog.services.filtration.Page;
import com.leverxblog.dao.repository.ArticleRepository;
import com.leverxblog.dao.repository.TagRepository;
import com.leverxblog.dao.repository.queries.ArticleQueryRepository;
import com.leverxblog.services.services.ArticleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleConverter articleConverter;
    private TagConverter tagConverter;
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;
    private ArticleQueryRepository articleQueryRepository;

    @Autowired
    public ArticleServiceImpl(ArticleConverter articleConverter, ArticleRepository articleRepository,
                              TagRepository tagRepository, TagConverter tagConverter, ArticleQueryRepository articleQueryRepository) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
        this.articleQueryRepository = articleQueryRepository;
    }

    @Override
    public String add(ArticleDto articleDto, UUID userId) {
        articleDto.setUserEntity_id(userId);

        for (TagDto tagDto : articleDto.getTags()) {
            TagEntity tagFromDatabase = tagRepository.findByName(tagDto.getName());
            if (tagFromDatabase != null) {
                tagDto.setId(tagFromDatabase.getId());
            } else {
                tagRepository.save(tagConverter.convert(tagDto));
                TagEntity savedTag = tagRepository.findByName(tagDto.getName());
                tagDto.setId(savedTag.getId());
            }
        }
        articleDto.setCreatedAt(new Date(System.currentTimeMillis()));
        ArticleEntity articleEntity = articleConverter.convert(articleDto);

        return String.valueOf(articleRepository.save(articleEntity).getId());
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleRepository.findAll().stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getByPublicStatus() {
        return articleRepository.getByStatus(Status.PUBLIC).stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto getById(UUID id) throws NotFoundException {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        return articleConverter.convert(articleEntity);
    }

    @Override
    public void delete(UUID id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<ArticleDto> getByUserId(UUID userId) {
        return articleRepository.getByUserEntity_id(userId).stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ArticleDto> findAll(Integer skip, Integer limit, String sort, String order) {
        return articleQueryRepository
                .findAll(new Page(skip, limit), new ArticleSortProvider(sort, order))
                .stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto getByTitle(String title) {
        return articleConverter.convert(articleRepository.getByTitle(title));
    }
}
