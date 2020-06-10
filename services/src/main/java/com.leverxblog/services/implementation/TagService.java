package com.leverxblog.services.implementation;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.converters.TagConverter;
import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.repository.TagRepository;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagService implements CrudService<TagDto> {
    private TagRepository tagRepository;
    private ArticleConverter articleConverter;
    private TagConverter tagConverter;

    @Autowired
    public TagService(TagRepository tagRepository, ArticleConverter articleConverter, TagConverter tagConverter) {
        this.tagRepository = tagRepository;
        this.articleConverter = articleConverter;
        this.tagConverter = tagConverter;
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

    public List<ArticleDto> getArticlesByTags(List<String> tagNames){
        return tagRepository.findByNameIn(tagNames).stream()
                .map(articleEntity -> articleConverter.convert(articleEntity))
                .collect(Collectors.toList());
    }
}
