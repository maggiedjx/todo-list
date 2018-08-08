package com.djx.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String taskNotFound) {
        super(taskNotFound);
    }
}
