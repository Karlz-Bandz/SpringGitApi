package com.gitapi.gitapi.exception;

import com.gitapi.gitapi.exception.git.GitErrorResponse;
import com.gitapi.gitapi.exception.git.GitNotFoundException;
import com.gitapi.gitapi.exception.git.GitUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GitUnauthorizedException.class)
    public ResponseEntity<GitErrorResponse> handleGitUnauthorizedException(GitUnauthorizedException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        GitErrorResponse gitErrorResponse = GitErrorResponse.builder()
                .status(httpStatus.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(gitErrorResponse, httpStatus);
    }

    @ExceptionHandler(GitNotFoundException.class)
    public ResponseEntity<GitErrorResponse> handleGitUserNotFoundException(GitNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        GitErrorResponse gitErrorResponse = GitErrorResponse.builder()
                .status(httpStatus.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(gitErrorResponse, httpStatus);
    }
}
