package com.leverxblog.exception;

import java.util.UUID;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException(UUID id) {
        super(String.format("Comment is not found with id : '%s'", id));
    }
}
