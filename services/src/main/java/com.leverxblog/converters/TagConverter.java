package com.leverxblog.converters;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.entity.ArticleEntity;
import com.leverxblog.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter {
    public TagEntity convert(TagDto tagDto){
     /*   List<ArticleEntity> articleEntities=new ArrayList<>();
        for(ArticleDto articleDto: tagDto.getArticles()){
            articleEntities.add(ArticleEntity.builder()
                    .id(articleDto.getId())
                    .build()
            );
        }
        //
      */
        return TagEntity.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
               // .articles(articleEntities)
                .build();
    }


    //add articles
    public TagDto convert(TagEntity tagEntity){
        return  TagDto.builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .build();
    }
}
