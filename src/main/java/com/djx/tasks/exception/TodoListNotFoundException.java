package com.djx.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoListNotFoundException extends RuntimeException {
    public TodoListNotFoundException(String message) {
        super(message);
    }
}
