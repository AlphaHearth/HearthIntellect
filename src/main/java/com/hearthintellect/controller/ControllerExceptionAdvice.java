package com.hearthintellect.controller;

import com.hearthintellect.exception.ErrorResponseException;
import com.hearthintellect.utils.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<Message> errorResponseHandler(ErrorResponseException thrownException) {
        return ResponseEntity.status(thrownException.getStatusCode()).body(
                new Message(thrownException.getStatusCode(), thrownException.getMessage())
        );
    }
}
