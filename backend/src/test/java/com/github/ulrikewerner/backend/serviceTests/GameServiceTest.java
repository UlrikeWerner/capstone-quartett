package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.*;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import com.github.ulrikewerner.backend.services.CardService;
import com.github.ulrikewerner.backend.services.GameService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    GameRepo gameRepo = mock(GameRepo.class);
    CardService cardService = mock(CardService.class);
    GameService gameService = new GameService(cardService, gameRepo);

    private final CardAttribute dummyCardAttribute = new CardAttribute("test", 1223, true, true);
    private final Card dummyCard1 = new Card("1", "Team1", NflLogoAcronym.NFL, new ArrayList<>(List.of(dummyCardAttribute)));
    private final Card dummyCard2 = new Card("2", "Team2", NflLogoAcronym.ARI, new ArrayList<>(List.of(dummyCardAttribute)));
    private final Card dummyCard3 = new Card("3", "Team3", NflLogoAcronym.BAL, new ArrayList<>(List.of(dummyCardAttribute)));
    private final Card dummyCard4 = new Card("4", "Team3", NflLogoAcronym.ATL, new ArrayList<>(List.of(dummyCardAttribute)));

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

        when(cardService.getAllCards()).thenReturn(cardList2);
        when(gameRepo.save(any(Game.class))).thenAnswer(invocation -> {
            Game savedGame = invocation.getArgument(0);
            savedGame.setId(newGame.getId());
            return savedGame;
        });

        Game actualGame= gameService.startNewGame();

        verify(cardService).getAllCards();
        verify(gameRepo).save(any(Game.class));
        assertEquals(newGame.getId(), actualGame.getId());
        assertEquals(newGame.getPlayerCards().size(), actualGame.getPlayerCards().size());
        assertEquals(newGame.getOpponentCards().size(), actualGame.getOpponentCards().size());
        assertTrue(actualGame.isPlayerTurn());
        assertFalse(actualGame.isFinished());
    }

    @Test
    void getOpenGames_shouldReturnAnEmptyList_whenNoGameAreOpen(){
        Deck deck1 = new Deck(List.of(dummyCard1, dummyCard2));
        Deck deck2 = new Deck(List.of(dummyCard3, dummyCard4));

        Game testGame = new Game(deck1, deck2);
        testGame.setFinished(true);

        when(gameRepo.findAll()).thenReturn(List.of(testGame));

        List<Game> actuelGameList = gameService.getOpenGames();

        verify(gameRepo).findAll();
        assertEquals(List.of(), actuelGameList);
    }

    @Test
    void getOpenGames_shouldReturnListOfGames_whenThereAreOpenGames(){
        Deck deck1 = new Deck(List.of(dummyCard1, dummyCard2));
        Deck deck2 = new Deck(List.of(dummyCard3, dummyCard4));

        Game testGame1 = new Game(deck1, deck2);
        Game testGame2 = new Game(deck2, deck1);
        Game testGame3 = new Game(deck1, deck2);
        testGame2.setFinished(true);

        when(gameRepo.findAll()).thenReturn(List.of(testGame1, testGame2, testGame3));

        List<Game> actuelGameList = gameService.getOpenGames();

        verify(gameRepo).findAll();
        assertEquals(List.of(testGame3, testGame1), actuelGameList);
    }

    @Test
    void getGameById_shouldReturnAnOptionalOfTheRightGame(){
        Deck deck1 = new Deck(List.of(dummyCard1, dummyCard2));
        Deck deck2 = new Deck(List.of(dummyCard3, dummyCard4));

        Game testGame = new Game(deck1, deck2);
        String id = testGame.getId();

        when(gameRepo.findById(id)).thenReturn(Optional.of(testGame));

        Optional<Game> optionalGame = gameService.getGameById(id);

        verify(gameRepo).findById(id);
        assertEquals(Optional.of(testGame), optionalGame);
    }

    @Test
    void getGameById_shouldReturnEmptyOptional_WhenTheIdIsNotFound(){
        when(gameRepo.findById("quatschId")).thenReturn(Optional.empty());

        Optional<Game> optionalGame = gameService.getGameById("quatschId");

        verify(gameRepo).findById("quatschId");
        assertEquals(Optional.empty(), optionalGame);
    }
}
