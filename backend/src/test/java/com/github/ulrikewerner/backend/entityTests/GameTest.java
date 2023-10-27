package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class GameTest {

    @Test
    void Game_constructorCreateCorrectObject() {
        CardAttribute cardAttribute1 = new CardAttribute("test", 2542, true, true);
        List<Card> cardList = List.of(new Card("1", "Flensburg Seemöwen", NflLogoAcronym.NFL, new ArrayList<>(List.of(cardAttribute1))));

        CardAttribute cardAttribute2 = new CardAttribute("test2", 25, false, false);
        List<Card> cardList2 = List.of(new Card("2", "Kieler Seemuscheln", NflLogoAcronym.KC, new ArrayList<>(List.of(cardAttribute2))));

        Deck player = new Deck(cardList);
        Deck opponent = new Deck(cardList2);

        Game testGame = new Game(player, opponent);

        assertNull(testGame.getId());
        assertTrue(testGame.isPlayerTurn());
        assertEquals(testGame.getPlayerCards(), player);
        assertEquals(testGame.getOpponentCards(), opponent);
        assertFalse(testGame.isFinished());
    }
}
