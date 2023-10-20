package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import com.github.ulrikewerner.backend.services.CardService;
import com.github.ulrikewerner.backend.services.GameService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    GameRepo gameRepo = mock(GameRepo.class);
    CardService cardservice = mock(CardService.class);
    GameService gameService = new GameService(cardservice, gameRepo);

    public Card dummyCard1 = new Card("1", "Team1", 12.23f);
    public Card dummyCard2 = new Card("2", "Team2", 12.23f);
    public Card dummyCard3 = new Card("3", "Team3", 12.23f);
    public Card dummyCard4 = new Card("4", "Team3", 12.23f);

    @Test
    void startNewGame_expectOneGame() {
        List<Card> cardList2 = List.of(dummyCard1, dummyCard2, dummyCard3, dummyCard4);

        List<Card> deckList = List.of(dummyCard2, dummyCard3);
        List<Card> deckList2 = List.of(dummyCard1, dummyCard4);

        Deck deck1 = new Deck(deckList);
        Deck deck2 = new Deck(deckList2);

        Game newGame = new Game(deck1, deck2);
        newGame.setId("1");
        newGame.setPlayerTurn(true);
        newGame.setFinished(false);

        when(cardservice.getAllCards()).thenReturn(cardList2);
        when(gameRepo.save(any(Game.class))).thenAnswer(invocation -> {
            Game savedGame = invocation.getArgument(0);
            savedGame.setId(newGame.getId());
            return savedGame;
        });

        Game actualGame= gameService.startNewGame();

        verify(cardservice).getAllCards();
        verify(gameRepo).save(any(Game.class));
        assertEquals(newGame.getId(), actualGame.getId());
        assertEquals(newGame.getPlayerCards().size(), actualGame.getPlayerCards().size());
        assertEquals(newGame.getOpponentCards().size(), actualGame.getOpponentCards().size());
        assertTrue(actualGame.isPlayerTurn());
        assertFalse(actualGame.isFinished());
    }
}