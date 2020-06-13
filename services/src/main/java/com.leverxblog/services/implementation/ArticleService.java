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
import com.leverxblog.services.ArticeServ;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService implements ArticeServ<ArticleDto> {
    private ArticleConverter articleConverter;
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;
    private TagConverter tagConverter;

    @Autowired
    public ArticleService(ArticleConverter articleConverter, ArticleRepository articleRepository, TagRepository tagRepository, TagConverter tagConverter) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public String add(ArticleDto articleDto, UUID userId) {
        articleDto.setUserEntity_id(userId);

        for(TagDto tagDto:articleDto.getTags()){
           TagEntity tagFromDatabase = tagRepository.findByName(tagDto.getName());
           if (tagFromDatabase!=null){
               tagDto.setId(tagFromDatabase.getId());
              // tagDto.addArticle(articleDto);
           }
           else {
             //  tagDto.addArticle(articleDto);
               tagRepository.save(tagConverter.convert(tagDto));
               tagDto.setId(tagRepository.findByName(tagDto.getName()).getId());
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
        return articleConverter.convert(articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id)));
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
