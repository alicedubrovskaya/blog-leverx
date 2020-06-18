package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.StatusDto;
import com.leverxblog.dto.TagDto;
import com.leverxblog.dto.UserDto;

import com.leverxblog.services.ArticleService;
import com.leverxblog.services.UserService;
import com.leverxblog.services.implementation.ArticleServiceImpl;
import com.leverxblog.services.implementation.UserServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addNewArticle(@RequestBody ArticleDto articleDto,
                                                             Authentication authentication) {
        String login = authentication.getName();
        UUID userId = userService.getByLogin(login).getId();
        userService.getByLogin(login);
        Map id = Collections.singletonMap("id", articleService.add(articleDto, userId));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ArticleDto>> getAll() {
        List<ArticleDto> articles = articleService.getAll();
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping(path = "/public")
    public ResponseEntity<List<ArticleDto>> getPublicArticles() {
        List<ArticleDto> articles = articleService.getByPublicStatus();
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping(path = "/sort")
    public ResponseEntity<List<ArticleDto>> getSortedArticles(
            @RequestParam(name = "skip", required = false, defaultValue = "1") Integer skip,
            @RequestParam(name = "limit", required = false, defaultValue = "3") Integer limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order
    ) {
        List<ArticleDto> articles = articleService.findAll(skip, limit, sort, order);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id,
                                       Authentication authentication)
            throws NotFoundException {
        ArticleDto articleDto = articleService.getById(id);
        UserDto user = userService.getByLogin(authentication.getName());

        if (articleDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if ((user.getId()).compareTo(articleDto.getUserEntity_id()) != 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<String> update(@PathVariable UUID userId, @RequestBody ArticleDto articleDto,
                                         Authentication authentication) throws NotFoundException {

        ArticleDto articleFromDatabase = articleService.getById(articleDto.getId());
        UserDto user = userService.getByLogin(authentication.getName());

        if (articleFromDatabase == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if ((user.getId()).compareTo(articleDto.getUserEntity_id()) != 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TagDto> tagDtos = new ArrayList<>();
        articleDto.getTags().forEach(
                tagDto -> {
                    tagDtos.add(TagDto.builder()
                            .id(tagDto.getId())
                            .name(tagDto.getName())
                            .build()
                    );
                }
        );
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
                                .firstName(articleFromDatabase.getUserDto().getFirstName())
                                .lastName(articleFromDatabase.getUserDto().getLastName())
                                .password(articleFromDatabase.getUserDto().getPassword())
                                .email(articleFromDatabase.getUserDto().getEmail())
                                .build()
                )
                .tags(tagDtos)
                .build();

        String id = articleService.add(articleDto2, userId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
