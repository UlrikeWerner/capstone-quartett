package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GameTest {

    @Test
    void Game_constructorCreateCorrectObject() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(new Card("1", "Flensburg Seemöwen", 25.42f));
        ArrayList<Card> cardList2 = new ArrayList<>();
        cardList2.add(new Card("2", "Kieler Seemuscheln", 23.25f));
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
