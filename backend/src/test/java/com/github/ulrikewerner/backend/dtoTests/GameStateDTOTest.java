package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.GameStateDTO;
import com.github.ulrikewerner.backend.entities.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GameStateDTOTest {
    private final CardAttribute flensburgCardAttribute = new CardAttribute("test", 2542, true);
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", new ArrayList<>(List.of(flensburgCardAttribute)));
    private final CardAttribute kielCardAttribute = new CardAttribute("test", 2325, true);
    private final Card kielCard = new Card("2", "Kieler Seemuscheln", new ArrayList<>(List.of(kielCardAttribute)));
    private final CardAttribute holnisCardAttribute = new CardAttribute("test", 2454, true);
    private final Card holnisCard = new Card("3", "Holniser Seesterne", new ArrayList<>(List.of(holnisCardAttribute)));

    @Test
    void GameStateDTO_constructor_createCorrectObject(){
        Deck player = new Deck(List.of(flensburgCard));
        Deck opponent = new Deck(List.of(kielCard, holnisCard));
        Game testGame = new Game(player, opponent);

        GameStateDTO testGameStateDTO = new GameStateDTO(testGame);

        assertEquals(1, testGameStateDTO.getScore().get("player"));
        assertEquals(2, testGameStateDTO.getScore().get("opponent"));
        assertEquals(NextTurnBy.PLAYER, testGameStateDTO.getNextTurnBy());
        assertEquals(flensburgCard.name(), testGameStateDTO.getNextPlayerCard().getName());
        assertEquals("25.42", testGameStateDTO.getNextPlayerCard().getAttributes().get("test"));

        testGame.setPlayerTurn(false);
        GameStateDTO testGameStateDTO2 = new GameStateDTO(testGame);
        assertEquals(NextTurnBy.OPPONENT, testGameStateDTO2.getNextTurnBy());
    }
}
