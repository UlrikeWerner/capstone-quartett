package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import com.github.ulrikewerner.backend.services.CardService;
import com.github.ulrikewerner.backend.services.GameService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    GameRepo gameRepo = mock(GameRepo.class);
    CardService cardservice = mock(CardService.class);
    GameService gameService = new GameService(cardservice, gameRepo);

    public Card c1 = new Card("1", "Team1", 12.23f);
    public Card c2 = new Card("2", "Team2", 12.23f);
    public Card c3 = new Card("3", "Team3", 12.23f);
    public Card c4 = new Card("4", "Team3", 12.23f);

    @Test
    void startNewGame_expectOneGame() {
        ArrayList<Card> cardList2 = new ArrayList<>();
        cardList2.add(c1);
        cardList2.add(c2);
        cardList2.add(c3);
        cardList2.add(c4);

        ArrayList<Card> deckList = new ArrayList<>();
        deckList.add(c2);
        deckList.add(c3);
        ArrayList<Card> deckList2 = new ArrayList<>();
        deckList2.add(c1);
        deckList2.add(c4);

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
            savedGame.setOpponentCards(newGame.getOpponentCards());
            savedGame.setPlayerCards(newGame.getPlayerCards());
            return savedGame;
        });

        Game actualGame= gameService.startNewGame();

        verify(cardservice).getAllCards();
        verify(gameRepo).save(any(Game.class));
        assertEquals(newGame.getId(), actualGame.getId());
        assertEquals(newGame.getPlayerCards(), actualGame.getPlayerCards());
        assertEquals(newGame.getOpponentCards(), actualGame.getOpponentCards());
        assertEquals(newGame.isPlayerTurn(), actualGame.isPlayerTurn());
        assertEquals(newGame.isFinished(), actualGame.isFinished());
    }
}
