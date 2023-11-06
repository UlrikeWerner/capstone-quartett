package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Das Spiel ist vorbei!")
public class GameIsOverException extends Exception {
    public GameIsOverException(){
        super("Das Spiel ist vorbei!");
    }
}
