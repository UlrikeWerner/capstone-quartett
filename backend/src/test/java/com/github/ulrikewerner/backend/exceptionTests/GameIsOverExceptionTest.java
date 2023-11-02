package com.github.ulrikewerner.backend.exceptionTests;

import com.github.ulrikewerner.backend.exception.GameIsOverException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameIsOverExceptionTest {
    @Test
    void GameIsOverException_hasTheRightMessage(){
        GameIsOverException testException = new GameIsOverException();
        assertEquals("Das Spiel ist vorbei!", testException.getMessage());
    }
}
