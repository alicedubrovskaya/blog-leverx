package com.leverxblog.controllers.exception;

import javassist.NotFoundException;

import java.util.UUID;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(UUID id) {
        super(String.format("Comment is not found with id : '%s'", id));
    }
}
