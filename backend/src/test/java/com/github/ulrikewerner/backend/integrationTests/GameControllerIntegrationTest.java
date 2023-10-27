package com.github.ulrikewerner.backend.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ulrikewerner.backend.entities.*;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    GameRepo gameRepo;

    @Autowired
    ObjectMapper objectMapper;

    CardAttribute kansasCardAttribute = new CardAttribute("Punkte pro Spiel", 2918, true, true);
    Card kansas = new Card("1", "Kansas City Chiefs", NflLogoAcronym.KC, new ArrayList<>(List.of(kansasCardAttribute)));

    private final CardAttribute piladelphiaCardAttribute = new CardAttribute("Punkte pro Spiel", 2806, true, true);
    private final Card philadelphia = new Card("2", "Philadelphia Eagles", NflLogoAcronym.PHI, new ArrayList<>(List.of(piladelphiaCardAttribute)));
    private final CardAttribute detroitCardAttribute = new CardAttribute("Punkte pro Spiel", 2665, true, true);
    private final Card detroit = new Card("1", "Detroit Lions", NflLogoAcronym.DET, new ArrayList<>(List.of(detroitCardAttribute)));
    private final Deck playerDeck = new Deck(List.of(detroit, kansas));
    private final Deck opponentDeck = new Deck(List.of(philadelphia));

    @Test
    @DirtiesContext
    void startNewGame_expectIdOfTheNewGame() throws Exception {

        String actual = mockMvc.perform(
                        post("/api/game")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {}
                                        """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(actual).isNotNull().isNotEqualTo("");
    }

    @Test
    @DirtiesContext
    void getGameState_whenNoGameBeFound_ThenReturnStatusNotFound() throws Exception {
        String id = "65366b856f9e37551acd81d3";

        mockMvc.perform(get("/api/game/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void getGameState_expectGameStateDTOFromTheRightGame() throws Exception {
        Game testGame = gameRepo.save(new Game(playerDeck, opponentDeck));

        mockMvc.perform(get("/api/game/" + testGame.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "score": {
                                "opponent": 1,
                                "player": 2
                            },
                            "nextTurnBy": "PLAYER",
                            "nextPlayerCard": {
                                "name": "Detroit Lions",
                                "logo": "DET",
                                "attributes": {
                                    "Punkte pro Spiel": "26.65"
                                }
                            }
                        }
                        """));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectGameNotFoundException() throws Exception {
        String id = "quatschId";

        mockMvc.perform(put("/api/game/" + id)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Touchdowns"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Spiel wurde nicht gefunden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectCategoryNotFoundException() throws Exception {
        Game testGame = gameRepo.save(new Game(playerDeck, opponentDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Yards"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Kategorie konnte nicht gefunden werden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectNotYourTurnException() throws Exception {
        Game game = new Game(playerDeck, opponentDeck);
        game.setPlayerTurn(false);
        Game testGame = gameRepo.save(game);

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Touchdowns"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Es ist nicht dein Zug!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectBadRequestException_WhenTheBodyIsEmpty() throws Exception {
        Game game = new Game(playerDeck, opponentDeck);
        game.setPlayerTurn(false);
        Game testGame = gameRepo.save(game);

        mockMvc.perform(put("/api/game/" + testGame.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectTurnDTO() throws Exception {
        Game testGame = gameRepo.save(new Game(playerDeck, opponentDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Punkte pro Spiel"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                         {
                            "score": {
                                "opponent": 2,
                                "player": 1
                            },
                            "nextTurnBy": "OPPONENT",
                            "nextPlayerCard": {
                                "name": "Kansas City Chiefs",
                                "logo": "KC",
                                "attributes": {
                                    "Punkte pro Spiel": "29.18"
                                }
                            },
                            "category": "Punkte pro Spiel",
                            "turnWinner": "OPPONENT",
                            "playerCard": {
                                "name": "Detroit Lions",
                                "logo": "DET",
                                "attributes": {
                                    "Punkte pro Spiel": "26.65"
                                }
                            },
                            "opponentCard": {
                                "name": "Philadelphia Eagles",
                                "logo": "PHI",
                                "attributes": {
                                    "Punkte pro Spiel": "28.06"
                                }
                            }
                         }
                """));
    }
}
