package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.services.implementation.ArticleServiceImpl;
import com.leverxblog.services.implementation.CommentServiceImpl;
import com.leverxblog.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class CommentController {

    private CommentServiceImpl commentServiceImpl;
    private UserService userService;
    private ArticleServiceImpl articleServiceImpl;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl, UserService userService, ArticleServiceImpl articleServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
        this.userService = userService;
        this.articleServiceImpl = articleServiceImpl;
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Object> addNewComment(@PathVariable UUID articleId, @RequestBody CommentDto commentDto,
                                                Authentication authentication) {

        String login = authentication.getName();
        UUID userId = userService.getByLogin(login).getId();

        commentDto.setUserId(userId);
        commentDto.setArticleId(articleId);

        Map id = Collections.singletonMap("id", commentServiceImpl.add(commentDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("{articleId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsOfArticle(@PathVariable UUID articleId) {
        List<CommentDto> comments = commentServiceImpl.getByArticleId(articleId);
        if (CollectionUtils.isEmpty(comments)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Object> getCommentOfArticleById(@PathVariable("articleId") UUID articleId,
                                                          @PathVariable("commentId") UUID commentId) throws Exception {

        CommentDto commentDto = commentServiceImpl.getByArticleAndCommentId(articleId, commentId);
        if (commentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID articleId, @PathVariable UUID commentId,
                                              Authentication authentication) throws Exception {

        CommentDto comment = commentServiceImpl.getById(commentId);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArticleDto article = articleServiceImpl.getById(comment.getArticleId());
        String login = authentication.getName();
        UUID userId = userService.getByLogin(login).getId();

        if ((comment.getUserId()).compareTo(userId) == 0 || (article.getUserEntity_id()).compareTo(userId) == 0) {
            commentServiceImpl.delete(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
