package com.leverxblog.controllers;

import com.leverxblog.dto.CommentDto;
import com.leverxblog.services.implementation.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addNewComment(@RequestBody CommentDto commentDto){
        Map id= Collections.singletonMap("id",commentService.add(commentDto));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
