package com.toy.fdiary.error.handler;

import com.toy.fdiary.error.exception.AuthException;
import com.toy.fdiary.error.response.GlobalErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<GlobalErrorResponse> handleAuthExceptionHandler(AuthException exception) {
        return ResponseEntity
                .status(exception.getErrorCode().getStatusCode())
                .body(GlobalErrorResponse.from(exception.getErrorCode().getErrorMessage()));
    }

}