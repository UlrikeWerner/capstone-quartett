package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends Exception{
    public GameNotFoundException(String gameId){
        super("Das Spiel mit der Id: " + gameId + " konnte nicht gefunden werden!");
    }
}
