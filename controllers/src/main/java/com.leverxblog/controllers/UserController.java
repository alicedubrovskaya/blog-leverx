package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.implementation.ArticleService;
import com.leverxblog.services.implementation.UserService;
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
    private UserService userService;
    private ArticleService articleService;

    @Autowired
    public UserController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

 /*   @PostMapping(path = "/add")
    public ResponseEntity<Object> addNewUser(@RequestBody UserDto userDto) {
        Map id = Collections.singletonMap("id", userService.add(userDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

  */

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path="/me")
    public ResponseEntity<List<ArticleDto>> getMyArticles(Authentication authentication){
        UserDto user = userService.getByLogin(authentication.getName());

        List <ArticleDto> articles = articleService.getByUserId(user.getId());
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

}
