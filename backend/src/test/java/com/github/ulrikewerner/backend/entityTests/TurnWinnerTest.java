package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.TurnWinner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TurnWinnerTest {
    @Test
    void TurnWinner_expectThatTheValuesOfTheEnumAreNotNull() {
        assertNotNull(TurnWinner.valueOf("PLAYER"));
        assertNotNull(TurnWinner.valueOf("OPPONENT"));
        assertNotNull(TurnWinner.valueOf("DRAW"));
    }
}
