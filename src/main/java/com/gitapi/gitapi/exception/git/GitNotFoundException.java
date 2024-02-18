package com.gitapi.gitapi.exception.git;

public class GitNotFoundException extends RuntimeException {
    public GitNotFoundException(String message) {
        super(message);
    }
}
