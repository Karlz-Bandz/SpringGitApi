package com.gitapi.gitapi.exception.git;

public class GitUnauthorizedException extends RuntimeException {
    public GitUnauthorizedException(String message) {
        super(message);
    }
}
