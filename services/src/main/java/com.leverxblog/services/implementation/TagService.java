package com.leverxblog.services.implementation;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.converters.TagConverter;
import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.repository.ArticleRepository;
import com.leverxblog.repository.TagRepository;
import com.leverxblog.repository.TagRepositoryQuery;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagService implements CrudService<TagDto> {
    private TagRepository tagRepository;
    private ArticleRepository articleRepository;
    private TagConverter tagConverter;
    private ArticleConverter articleConverter;
    private TagRepositoryQuery tagRepositoryQuery;
    private ArticleService articleService;

    @Autowired
    public TagService(TagRepository tagRepository, ArticleRepository articleRepository, TagConverter tagConverter, ArticleConverter articleConverter, TagRepositoryQuery tagRepositoryQuery, ArticleService articleService) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.tagRepositoryQuery = tagRepositoryQuery;
        this.tagConverter = tagConverter;
        this.articleService = articleService;
    }

    @Override
    public String add(TagDto tagDto) {
        return String.valueOf(tagRepository.save(tagConverter.convert(tagDto)).getId());
    }

    @Override
    public List<TagDto> getAll() {
        return null;
    }

    @Override
    public TagDto getById(UUID id) throws Exception {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(tagEntity -> tagConverter.convert(tagEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ArticleDto> getArticlesByTags(List<String> tagNames)  {
        List<Long> tagIds= tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName).getId())
                .collect(Collectors.toList());
        List<ArticleEntity> articlesList=tagRepositoryQuery.findByTagsIds(tagIds);

       /* List<ArticleDto> articles = articlesList.stream()
                .map(articleEntity -> articleConverter.convert(articleRepository.findById(articleEntity.getId()).get()))
                .collect(Collectors.toList());
        */
        return null;
    }

    @Transactional
    public Map<String, Integer> amountOfArticlesByTag(TagDto tagDto){
        Map<String, Integer> tags = new HashMap<>();
        Integer count = tagRepositoryQuery.amountOfArticlesByTagId(tagDto.getId());
        String name = tagRepository.findById(tagDto.getId()).get().getName();
        tags.put(name, count);
        return tags;
    }
}
