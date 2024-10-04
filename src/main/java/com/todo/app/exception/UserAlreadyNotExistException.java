package com.todo.app.exception;

public class UserAlreadyNotExistException extends RuntimeException {
    public UserAlreadyNotExistException(String message) {
        super(message);
    }
}
