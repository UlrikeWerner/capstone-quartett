package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.OpenGameDTO;
import com.github.ulrikewerner.backend.entities.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenGameDTOTest {
    CardAttribute attribute1Card1 = new CardAttribute("test1", 23, false, true);
    CardAttribute attribute1Card2 = new CardAttribute("test1", 13, false, true);
    CardAttribute attribute2Card1 = new CardAttribute("test2", 2323, true, true);
    CardAttribute attribute2Card2 = new CardAttribute("test2", 1323, true, true);
    Card card1 = new Card("1", "card1", NflLogoAcronym.DET, new ArrayList<>(List.of(attribute1Card1, attribute2Card1)));
    Card card2 = new Card("2", "card2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute1Card2, attribute2Card2)));
    Deck testDeckPlayer = new Deck(List.of(card1));
    Deck testDeckOpponent = new Deck(List.of(card2));
    Game testGame = new Game(testDeckPlayer, testDeckOpponent);

    @Test
    void OpenGameDTO_constructor_createCorrectDTO() {
        OpenGameDTO testOpenGameDTO = new OpenGameDTO(testGame);

        assertEquals(testGame.getId(), testOpenGameDTO.getGameId());
        assertEquals(testGame.getTitle(), testOpenGameDTO.getTitle());
    }
}
