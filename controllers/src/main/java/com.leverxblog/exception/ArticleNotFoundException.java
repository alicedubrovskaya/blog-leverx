package com.leverxblog.exception;

import java.util.UUID;

public class ArticleNotFoundException extends Exception {
    private UUID id;

    public ArticleNotFoundException(UUID id) {
        super(String.format("Article is not found with id : '%s'", id));
    }
}
