package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Spiel wurde nicht gefunden!")
public class GameNotFoundException extends Exception{
    public GameNotFoundException(String gameId){
        super("Das Spiel mit der Id: " + gameId + " konnte nicht gefunden werden!");
    }
}
