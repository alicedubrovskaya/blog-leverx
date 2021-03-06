package com.leverxblog.services.services.implementation;

import com.leverxblog.services.converters.TagConverter;
import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.TagDto;
import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.dao.entity.TagEntity;
import com.leverxblog.dao.repository.TagRepository;
import com.leverxblog.dao.repository.queries.TagQueryRepository;
import com.leverxblog.services.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;
    private TagConverter tagConverter;
    private TagQueryRepository tagQueryRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagConverter tagConverter, TagQueryRepository tagQueryRepository) {
        this.tagRepository = tagRepository;
        this.tagQueryRepository = tagQueryRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public String add(TagDto tagDto) {
        TagEntity tagEntity = tagConverter.convert(tagDto);
        return String.valueOf(tagRepository.save(tagEntity).getId());
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(tagEntity -> tagConverter.convert(tagEntity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ArticleDto> getArticlesByTags(List<String> tagNames) {
        List<Long> tagIds = tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName).getId())
                .collect(Collectors.toList());
        List<ArticleEntity> articlesList = tagQueryRepository.findByTagsIds(tagIds);
        return null;
    }

    @Override
    @Transactional
    public Map<String, Integer> amountOfArticlesByTag(TagDto tagDto) {
        Map<String, Integer> tags = new HashMap<>();
        Integer count = tagQueryRepository.amountOfArticlesByTagId(tagDto.getId());
        String name = tagRepository.findById(tagDto.getId()).get().getName();
        tags.put(name, count);
        return tags;
    }
}
