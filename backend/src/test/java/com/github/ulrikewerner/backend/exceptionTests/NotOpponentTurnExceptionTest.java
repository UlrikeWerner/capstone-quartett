package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.NotOpponentTurnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotOpponentTurnExceptionTest {
    @Test
    void NotOpponentTurnException_hasTheRightMessage(){
        NotOpponentTurnException testException = new NotOpponentTurnException();
        assertEquals("Der Gegner ist nicht am Zug!", testException.getMessage());
    }
}