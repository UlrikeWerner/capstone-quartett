package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Kategorie konnte nicht gefunden werden!")
public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String category){
        super("Die Kategorie '" + category + "' konnte nicht ausgewertet werden!");
    }
}
