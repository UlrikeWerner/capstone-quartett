package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotYourTurnException extends Exception{
    public NotYourTurnException(){
        super("Es ist nicht dein Zug!");
    }
}
