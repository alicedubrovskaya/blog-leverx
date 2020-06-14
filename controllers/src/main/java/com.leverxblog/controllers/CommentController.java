package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.services.implementation.ArticleServiceImpl;
import com.leverxblog.services.implementation.CommentServiceImpl;
import com.leverxblog.services.implementation.UserServiceImpl;
import javassist.NotFoundException;
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

    private final CommentServiceImpl commentServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final ArticleServiceImpl articleServiceImpl;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl, UserServiceImpl userServiceImpl,
                             ArticleServiceImpl articleServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.articleServiceImpl = articleServiceImpl;
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Map<String, String>> addNewComment(@PathVariable UUID articleId,
                                                             @RequestBody CommentDto commentDto,
                                                             Authentication authentication) {

        String login = authentication.getName();
        UUID userId = userServiceImpl.getByLogin(login).getId();

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
    public ResponseEntity<CommentDto> getCommentOfArticleById(@PathVariable("articleId") UUID articleId,
                                                              @PathVariable("commentId") UUID commentId) {

        CommentDto commentDto = commentServiceImpl.getByArticleAndCommentId(articleId, commentId);
        if (commentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID articleId, @PathVariable UUID commentId,
                                              Authentication authentication) throws NotFoundException {

        CommentDto comment = commentServiceImpl.getById(commentId);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArticleDto article = articleServiceImpl.getById(comment.getArticleId());
        String login = authentication.getName();
        UUID userId = userServiceImpl.getByLogin(login).getId();

        if ((comment.getUserId()).compareTo(userId) == 0 || (article.getUserEntity_id()).compareTo(userId) == 0) {
            commentServiceImpl.delete(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "{articleId}/comments/sort")
    public ResponseEntity<List<CommentDto>> getSortedComments(
            @PathVariable UUID articleId,
            @RequestParam(name = "skip", required = false, defaultValue = "1") Integer skip,
            @RequestParam(name = "limit", required = false, defaultValue = "3") Integer limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order
    ) {
        List<CommentDto> comments = commentServiceImpl.findAll(skip, limit, sort, order);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
