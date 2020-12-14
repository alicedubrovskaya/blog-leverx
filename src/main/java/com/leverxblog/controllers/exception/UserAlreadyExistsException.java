package com.leverxblog.controllers.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String login){
        super(String.format("User already exists with login : '%s'", login));
    }
}
