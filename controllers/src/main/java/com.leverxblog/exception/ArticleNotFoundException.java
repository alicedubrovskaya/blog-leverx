package com.leverxblog.exception;

import javassist.NotFoundException;

import java.util.UUID;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(UUID id) {
        super(String.format("Article is not found with id : '%s'", id));
    }
}
