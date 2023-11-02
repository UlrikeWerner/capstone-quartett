package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.FinalGameResultDTO;
import com.github.ulrikewerner.backend.entities.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FinalGameResultDTOTest {
    private final CardAttribute standardAttribute = new CardAttribute("test", 2542, true, true);
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", NflLogoAcronym.NFL, new ArrayList<>(List.of(standardAttribute)));
    private final Card kielCard = new Card("2", "Kieler Seemuscheln", NflLogoAcronym.BUF, new ArrayList<>(List.of(standardAttribute)));
    private final Card holnisCard = new Card("3", "Holniser Seesterne", NflLogoAcronym.ARI, new ArrayList<>(List.of(standardAttribute)));

    @Test
    void finalGameResult_smallConstructor_createCorrectObject(){
        Deck player = new Deck(List.of());
        Deck opponent = new Deck(List.of(kielCard, holnisCard));
        Game testGame = new Game(player, opponent);

        FinalGameResultDTO testFinalGameResultDTO = new FinalGameResultDTO(testGame);

        assertEquals(0, testFinalGameResultDTO.getScore().get("player"));
        assertEquals(2, testFinalGameResultDTO.getScore().get("opponent"));
        assertNull(testFinalGameResultDTO.getCategory());
        assertNull(testFinalGameResultDTO.getTurnWinner());
        assertNull(testFinalGameResultDTO.getPlayerCard());
        assertNull(testFinalGameResultDTO.getOpponentCard());
        assertNull(testFinalGameResultDTO.getWinner());
    }

    @Test
    void finalGameResult_fullConstructor_createCorrectObject(){
        Deck player = new Deck(List.of());
        Deck opponent = new Deck(List.of(holnisCard, kielCard, flensburgCard));
        Game testGame = new Game(player, opponent);

        FinalGameResultDTO testFinalGameResultDTO = new FinalGameResultDTO(testGame, "test", TurnWinner.OPPONENT, flensburgCard, kielCard, GameWinner.OPPONENT);

        assertEquals(0, testFinalGameResultDTO.getScore().get("player"));
        assertEquals(3, testFinalGameResultDTO.getScore().get("opponent"));
        assertEquals("test", testFinalGameResultDTO.getCategory());
        assertEquals(TurnWinner.OPPONENT, testFinalGameResultDTO.getTurnWinner());
        assertEquals(GameWinner.OPPONENT ,testFinalGameResultDTO.getWinner());

        assertNotNull(testFinalGameResultDTO.getPlayerCard());
        assertEquals("Flensburg Seemöwen", testFinalGameResultDTO.getPlayerCard().getName());
        assertNotNull(testFinalGameResultDTO.getOpponentCard());
        assertEquals("Kieler Seemuscheln", testFinalGameResultDTO.getOpponentCard().getName());
    }
}
