package com.leverxblog.controllers.exception;

public class InsufficientDataException extends Exception {
    public InsufficientDataException() {
        super("Insufficient data, please enter missing data");
    }
}
