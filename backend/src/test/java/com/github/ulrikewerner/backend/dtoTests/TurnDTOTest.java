package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.TurnDTO;
import com.github.ulrikewerner.backend.entities.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnDTOTest {
    private final CardAttribute standardAttribute = new CardAttribute("test", 2542, true, true);
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", NflLogoAcronym.NFL, new ArrayList<>(List.of(standardAttribute)));
    private final Card kielCard = new Card("2", "Kieler Seemuscheln", NflLogoAcronym.BUF, new ArrayList<>(List.of(standardAttribute)));
    private final Card holnisCard = new Card("3", "Holniser Seesterne", NflLogoAcronym.ARI, new ArrayList<>(List.of(standardAttribute)));
    private final Card luebeckCard = new Card("4", "Lübecker Meerjungfrauen", NflLogoAcronym.NO, new ArrayList<>(List.of(standardAttribute)));

    @Test
    void GameStateDTO_smallConstructor_createCorrectObject(){
        Deck player = new Deck(List.of(flensburgCard));
        Deck opponent = new Deck(List.of(kielCard, holnisCard));
        Game testGame = new Game(player, opponent);

        TurnDTO testTurnStateDTO = new TurnDTO(testGame);

        assertEquals(1, testTurnStateDTO.getScore().get("player"));
        assertEquals(2, testTurnStateDTO.getScore().get("opponent"));
        assertEquals(NextTurnBy.PLAYER, testTurnStateDTO.getNextTurnBy());
        assertEquals(flensburgCard.name(), testTurnStateDTO.getNextPlayerCard().getName());
        assertEquals(flensburgCard.logo(), testTurnStateDTO.getNextPlayerCard().getLogo());
        assertEquals("25.42", testTurnStateDTO.getNextPlayerCard().getAttributes().get("test"));
        assertNull(testTurnStateDTO.getCategory());
        assertNull(testTurnStateDTO.getTurnWinner());
        assertNull(testTurnStateDTO.getPlayerCard());
        assertNull(testTurnStateDTO.getOpponentCard());
    }

    @Test
    void GameStateDTO_fullConstructor_createCorrectObject(){
        Deck player = new Deck(List.of(flensburgCard, luebeckCard));
        Deck opponent = new Deck(List.of(kielCard, holnisCard));
        Game testGame = new Game(player, opponent);

        TurnDTO testTurnStateDTO = new TurnDTO(testGame, "test", TurnWinner.DRAW, flensburgCard, kielCard);

        assertEquals(2, testTurnStateDTO.getScore().get("player"));
        assertEquals(2, testTurnStateDTO.getScore().get("opponent"));
        assertEquals(NextTurnBy.PLAYER, testTurnStateDTO.getNextTurnBy());
        assertEquals(flensburgCard.name(), testTurnStateDTO.getNextPlayerCard().getName());
        assertEquals(flensburgCard.logo(), testTurnStateDTO.getNextPlayerCard().getLogo());
        assertEquals("25.42", testTurnStateDTO.getNextPlayerCard().getAttributes().get("test"));
        assertEquals("test", testTurnStateDTO.getCategory());
        assertEquals(TurnWinner.DRAW, testTurnStateDTO.getTurnWinner());
        assertEquals(flensburgCard, testTurnStateDTO.getPlayerCard());
        assertEquals(kielCard, testTurnStateDTO.getOpponentCard());
    }
}
