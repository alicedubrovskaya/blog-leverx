package com.leverxblog.controllers.exception;

import javassist.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String email) {
        super(String.format("User is not found with email : '%s'", email));
    }
}
