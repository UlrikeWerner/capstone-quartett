package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.GameTitleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTitleDTOTest {
    @Test
    void GameTitleDTO_constructor_createCorrectDTO() {
        GameTitleDTO testGameTitleDTO = new GameTitleDTO("Test Title");

        assertEquals( "Test Title", testGameTitleDTO.title());
    }
}
