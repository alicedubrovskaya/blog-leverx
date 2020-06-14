package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.implementation.ArticleServiceImpl;
import com.leverxblog.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final ArticleServiceImpl articleServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, ArticleServiceImpl articleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.articleServiceImpl = articleServiceImpl;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userServiceImpl.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<ArticleDto>> getMyArticles(Authentication authentication) {
        UserDto user = userServiceImpl.getByLogin(authentication.getName());

        List<ArticleDto> articles = articleServiceImpl.getByUserId(user.getId());
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
