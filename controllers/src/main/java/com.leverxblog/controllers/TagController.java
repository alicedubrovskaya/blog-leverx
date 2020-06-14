package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.services.implementation.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class TagController {

    private TagServiceImpl tagServiceImpl;

    @Autowired
    public TagController(TagServiceImpl tagServiceImpl) {
        this.tagServiceImpl = tagServiceImpl;
    }

    @PostMapping("/addTag")
    public ResponseEntity<Object> addNewTag(@RequestBody TagDto tagDto) {
        Map id = Collections.singletonMap("id", tagServiceImpl.add(tagDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/cloud")
    public ResponseEntity<List<Map<String, Integer>>> getCloudOfTags() {
        List<TagDto> tags = tagServiceImpl.findAll();
        List<Map<String, Integer>> tagsCloud = tags.stream()
                .map(tagDto -> tagServiceImpl.amountOfArticlesByTag(tagDto))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagsCloud, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticlesByTags(@RequestParam List<String> tags) {
        List<ArticleDto> articles = tagServiceImpl.getArticlesByTags(tags);
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(articles, HttpStatus.OK);
    }
}
