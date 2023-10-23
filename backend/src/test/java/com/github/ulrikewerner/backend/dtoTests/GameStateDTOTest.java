package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.GameStateDTO;
import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.NextTurnBy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GameStateDTOTest {
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", 25.42);
    private final Card kielCard = new Card("2", "Kieler Seemuscheln", 23.25);
    private final Card holnisCard = new Card("3", "Holniser Seesterne", 24.54);

    @Test
    void GameStateDTO_constructor_createCorrectObject(){
        Deck player = new Deck(List.of(flensburgCard));
        Deck opponent = new Deck(List.of(kielCard, holnisCard));
        Game testGame = new Game(player, opponent);

        GameStateDTO testGameStateDTO = new GameStateDTO(testGame);

        assertEquals(1, testGameStateDTO.getScore().get("player"));
        assertEquals(2, testGameStateDTO.getScore().get("opponent"));
        assertEquals(NextTurnBy.PLAYER, testGameStateDTO.getNextTurnBy());
        assertEquals(flensburgCard, testGameStateDTO.getNextPlayerCard());

        testGame.setPlayerTurn(false);
        GameStateDTO testGameStateDTO2 = new GameStateDTO(testGame);
        assertEquals(NextTurnBy.OPPONENT, testGameStateDTO2.getNextTurnBy());

    }
}
