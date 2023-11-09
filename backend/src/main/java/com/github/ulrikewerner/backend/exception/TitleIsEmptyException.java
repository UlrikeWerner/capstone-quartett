package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Der Title darf nicht leer sein!")
public class TitleIsEmptyException extends Exception{
    public TitleIsEmptyException(){
        super("Der Title darf nicht leer sein!");
    }
}
