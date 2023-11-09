package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.TitleIsEmptyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleIsEmptyExceptionTest {
    @Test
    void TitleIsEmptyException_hasTheRightMessage(){
        TitleIsEmptyException testException = new TitleIsEmptyException();
        assertEquals("Der Title darf nicht leer sein!", testException.getMessage());
    }
}
