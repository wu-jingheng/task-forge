package com.moxie.task_forge.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String idOrUsername) {
        super(String.format("User of ID/username [%s] is not found", idOrUsername));
    }
}
