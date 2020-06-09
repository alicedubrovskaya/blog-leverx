package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.UserDto;

import com.leverxblog.services.implementation.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Object> addNewArticle(@RequestBody ArticleDto articleDto) {
        Map id = Collections.singletonMap("id", articleService.add(articleDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAll() {
        List<ArticleDto> articles = articleService.getAll();
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id){
        articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<Object> update(@PathVariable UUID userId, @RequestBody ArticleDto articleDto) throws Exception {

        ArticleDto articleDto1 = articleService.getById(articleDto.getId());
        if (articleDto1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //!!!UUID comparison
      //  if (articleDto1.getUserEntity_id()!=userId){
        //    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        //}
        ArticleDto articleDto2 = ArticleDto.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .text(articleDto.getText())
                .status(StatusDto.valueOf(articleDto.getStatus().name()))
                .createdAt(articleDto.getCreatedAt())
                .updatedAt(articleDto.getUpdatedAt())
                .userEntity_id(userId)
                .userDto(
                        UserDto.builder()
                                .id(userId)
                                .firstName(articleDto1.getUserDto().getFirstName())
                                .lastName(articleDto1.getUserDto().getLastName())
                                .password(articleDto1.getUserDto().getPassword())
                                .email(articleDto1.getUserDto().getEmail())
                                .build()
                )
                .build();

        String id = articleService.add(articleDto2);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
