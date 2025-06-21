package com.moxie.task_forge.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String id) {
        super(String.format("Task of ID [%s] is not found", id));
    }
}
