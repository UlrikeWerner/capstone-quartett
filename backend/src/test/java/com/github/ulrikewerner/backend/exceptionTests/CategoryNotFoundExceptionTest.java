package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.CategoryNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryNotFoundExceptionTest {
    @Test
    void CategoryNotFoundException_hasTheRightMessage(){
        String category = "  ";
        CategoryNotFoundException testException = new CategoryNotFoundException(category);
        assertEquals("Die Kategorie '  ' konnte nicht ausgewertet werden!", testException.getMessage());

        String category2 = "testKategorie";
        CategoryNotFoundException testException2 = new CategoryNotFoundException(category2);
        assertEquals("Die Kategorie 'testKategorie' konnte nicht ausgewertet werden!", testException2.getMessage());
    }
}
