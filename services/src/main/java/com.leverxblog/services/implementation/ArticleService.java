package com.leverxblog.services.implementation;

import com.leverxblog.converters.ArticleConverter;
import com.leverxblog.converters.TagConverter;
import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.TagEntity;
import com.leverxblog.exception.ArticleNotFoundException;
import com.leverxblog.repository.ArticleRepository;
import com.leverxblog.repository.TagRepository;
import com.leverxblog.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService implements CrudService<ArticleDto> {
    private ArticleConverter articleConverter;
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;

    @Autowired
    public ArticleService(ArticleConverter articleConverter, ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleConverter = articleConverter;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public String add(ArticleDto articleDto) {
 /*     List<TagEntity> tagsFromDatabase=new ArrayList<>();
        for(TagDto tagDto:articleDto.getTags()){
           TagEntity tagFromDatabase = tagRepository.findByName(tagDto.getName());
           if (tagFromDatabase!=null){
               tagsFromDatabase.add(tagFromDatabase);
           }
        }

        for (TagEntity tagFromDatabase:tagsFromDatabase) {
            for (TagDto tagDto: articleDto.getTags()){
                if ((tagFromDatabase.getName()).equals(tagDto.getName())){
                    tagDto.setId(tagFromDatabase.getId());
                }
            }
        }
/

  */
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
    public ArticleDto getById(UUID id) throws Exception {
        return articleConverter.convert(articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id)));
    }

    @Override
    public void delete(UUID id) {
        articleRepository.deleteById(id);
    }


}
