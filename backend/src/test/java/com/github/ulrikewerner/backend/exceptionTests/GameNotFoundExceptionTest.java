package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.GameNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameNotFoundExceptionTest {
    @Test
    void GameNotFoundException_hasTheRightMessage(){
        String gameId = "123";
        GameNotFoundException testException = new GameNotFoundException(gameId);
        assertEquals("Das Spiel mit der Id: " + gameId + " konnte nicht gefunden werden!", testException.getMessage());
    }
}
