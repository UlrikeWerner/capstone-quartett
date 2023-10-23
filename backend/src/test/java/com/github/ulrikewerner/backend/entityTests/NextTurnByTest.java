package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.NextTurnBy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextTurnByTest {
    @Test
    void NextTurnBy_expectThatTheValuesOfTheEnumAreNotNull() {
        assertNotNull(NextTurnBy.valueOf("PLAYER"));
        assertNotNull(NextTurnBy.valueOf("OPPONENT"));
    }
}
