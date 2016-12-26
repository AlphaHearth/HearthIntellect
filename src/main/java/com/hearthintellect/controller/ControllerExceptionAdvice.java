package com.hearthintellect.controller;

import com.hearthintellect.controller.exception.EntityNotFoundException;
import com.hearthintellect.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    private static final String ENTITY_NOT_FOUND_PROMPT = "%s with given ID `%s` does not exist.";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Message entityNotFoundHandler(EntityNotFoundException thrownException) {
        return new Message(404, String.format(
                ENTITY_NOT_FOUND_PROMPT, thrownException.getEntityName(), thrownException.getEntityId()
        ));
    }
}
