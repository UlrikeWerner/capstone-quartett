package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GameTest {

    @Test
    void Game_constructorCreateCorrectObject() {
        List<Card> cardList = List.of(new Card("1", "Flensburg Seemöwen", 2542));
        List<Card> cardList2 = List.of(new Card("2", "Kieler Seemuscheln", 2325));
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
