package com.leverxblog.controllers.controllers;

import com.leverxblog.services.dto.ArticleDto;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.services.services.ArticleService;
import com.leverxblog.services.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<ArticleDto>> getMyArticles(Authentication authentication) {
        UserDto user = userService.getByLogin(authentication.getName());

        List<ArticleDto> articles = articleService.getByUserId(user.getId());
        if (CollectionUtils.isEmpty(articles)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
