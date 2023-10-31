package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Der Gegner ist nicht am Zug!")
public class NotOpponentTurnException extends Exception{
    public NotOpponentTurnException() {
        super("Der Gegner ist nicht am Zug!");
    }
}