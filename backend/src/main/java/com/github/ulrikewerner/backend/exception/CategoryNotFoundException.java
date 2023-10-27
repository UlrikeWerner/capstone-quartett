package com.github.ulrikewerner.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String category){
        super("Die Kategorie '" + category + "' konnte nicht ausgewertet werden!");
    }
}
