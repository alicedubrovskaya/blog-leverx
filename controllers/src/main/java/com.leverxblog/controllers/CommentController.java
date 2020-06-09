package com.leverxblog.controllers;

import com.leverxblog.dto.CommentDto;
import com.leverxblog.dto.UserDto;
import com.leverxblog.services.implementation.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.xml.ws.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Object> addNewComment(@PathVariable UUID articleId, @RequestBody CommentDto commentDto) {
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
    public ResponseEntity<Object> getCommentOfArticleById(@PathVariable("articleId") UUID articleId,
                                                          @PathVariable("commentId") UUID commentId) throws Exception{

       CommentDto commentDto=commentService.getByArticleAndCommentId(articleId,commentId);
        if (commentDto==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID articleId, @PathVariable UUID commentId) throws Exception{
        //to add checking authorship
        commentService.delete(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
