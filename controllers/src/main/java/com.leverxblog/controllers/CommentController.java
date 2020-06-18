package com.leverxblog.controllers;

import com.leverxblog.dto.ArticleDto;
import com.leverxblog.dto.CommentDto;
import com.leverxblog.services.ArticleService;
import com.leverxblog.services.CommentService;
import com.leverxblog.services.UserService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;


    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Map<String, String>> addNewComment(@PathVariable UUID articleId,
                                                             @RequestBody CommentDto commentDto,
                                                             Authentication authentication) {

        String login = authentication.getName();
        UUID userId = userService.getByLogin(login).getId();

        commentDto.setUserId(userId);
        commentDto.setArticleId(articleId);

        Map id = Collections.singletonMap("id", commentService.add(commentDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("{articleId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsOfArticle(@PathVariable UUID articleId) {
        List<CommentDto> comments = commentService.getByArticleId(articleId);
        if (CollectionUtils.isEmpty(comments)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentOfArticleById(@PathVariable("articleId") UUID articleId,
                                                              @PathVariable("commentId") UUID commentId) {

        CommentDto commentDto = commentService.getByArticleAndCommentId(articleId, commentId);
        if (commentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID articleId, @PathVariable UUID commentId,
                                              Authentication authentication) throws NotFoundException {

        CommentDto comment = commentService.getById(commentId);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArticleDto article = articleService.getById(comment.getArticleId());
        String login = authentication.getName();
        UUID userId = userService.getByLogin(login).getId();

        if ((comment.getUserId()).compareTo(userId) == 0 || (article.getUserEntity_id()).compareTo(userId) == 0) {
            commentService.delete(commentId);
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
        List<CommentDto> comments = commentService.findAll(skip, limit, sort, order);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
