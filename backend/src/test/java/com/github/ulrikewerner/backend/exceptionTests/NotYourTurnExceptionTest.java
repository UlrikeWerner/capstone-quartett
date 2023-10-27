package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.NotYourTurnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotYourTurnExceptionTest {
    @Test
    void NotYourTurnException_hasTheRightMessage(){
        NotYourTurnException testException = new NotYourTurnException();
        assertEquals("Es ist nicht dein Zug!", testException.getMessage());
    }
}
