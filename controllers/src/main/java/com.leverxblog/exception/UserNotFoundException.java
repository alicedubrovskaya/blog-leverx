package com.leverxblog.exception;

import java.util.UUID;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String email) {
        super(String.format("User is not found with email : '%s'", email));
    }
}
