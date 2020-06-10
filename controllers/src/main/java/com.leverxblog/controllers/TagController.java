package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.services.implementation.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticleByTags(@RequestParam List<String> tagNames) {
        List<ArticleDto> articles = tagService.getArticlesByTags(tagNames);
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(articles,HttpStatus.OK);
    }

    @PostMapping("/addTag")
    public ResponseEntity<Object> addNewTag(@RequestBody TagDto tagDto){
        Map id = Collections.singletonMap("id", tagService.add(tagDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
