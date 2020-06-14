package com.leverxblog.services.implementation;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.converters.TagConverter;
import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.Status;
import com.leverxblog.entity.TagEntity;
import com.leverxblog.exception.ArticleNotFoundException;
import com.leverxblog.repository.ArticleRepository;
import com.leverxblog.repository.TagRepository;
import com.leverxblog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService<ArticleDto> {
    private ArticleConverter articleConverter;
    private TagConverter tagConverter;
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;

    @Autowired
    public ArticleServiceImpl(ArticleConverter articleConverter, ArticleRepository articleRepository,
                              TagRepository tagRepository, TagConverter tagConverter) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
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
    public ArticleDto getById(UUID id) throws Exception {
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
}
