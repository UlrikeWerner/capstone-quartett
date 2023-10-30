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
    private final CardAttribute wrongCardAttribute = new CardAttribute("Punkte", 2665, true, true);
    private final Card wrongCard = new Card("1", "Detroit Lions", NflLogoAcronym.DET, new ArrayList<>(List.of(wrongCardAttribute)));
    private final Deck winnerDeck = new Deck(List.of(detroit, kansas));
    private final Deck loserDeck = new Deck(List.of(philadelphia));
    private final Deck wrongCategoryDeck = new Deck(List.of(wrongCard));

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
        Game testGame = gameRepo.save(new Game(winnerDeck, loserDeck));

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
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "category": "Touchdowns"
                        }
                        """))
                        .andExpect(status().isNotFound())
                        .andExpect(status().reason("Spiel wurde nicht gefunden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectCategoryNotFoundException_WhenPlayerCardHasNotTheCategory() throws Exception {
        Game testGame = gameRepo.save(new Game(wrongCategoryDeck, loserDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Punkte pro Spiel"
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Kategorie konnte nicht gefunden werden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectCategoryNotFoundException_WhenOpponentCardHasNotTheCategory() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, wrongCategoryDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Punkte pro Spiel"
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Kategorie konnte nicht gefunden werden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectCategoryNotFoundException_WhenCategoryIsBlank() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, loserDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": " "
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Kategorie konnte nicht gefunden werden!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectNotYourTurnException() throws Exception {
        Game game = new Game(winnerDeck, loserDeck);
        game.setPlayerTurn(false);
        Game testGame = gameRepo.save(game);

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Touchdowns"
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Es ist nicht dein Zug!"));
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectBadRequestException_WhenTheBodyIsEmpty() throws Exception {
        Game game = new Game(winnerDeck, loserDeck);
        game.setPlayerTurn(false);
        Game testGame = gameRepo.save(game);

        mockMvc.perform(put("/api/game/" + testGame.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void getTurnResult_expectTurnDTO_withPlayerAsTurnWinner() throws Exception {
        Game testGame = gameRepo.save(new Game(loserDeck, winnerDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Punkte pro Spiel"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                 {
                                    "score": {
                                        "opponent": 1,
                                        "player": 2
                                    },
                                    "nextTurnBy": "OPPONENT",
                                    "nextPlayerCard": {
                                        "name": "Philadelphia Eagles",
                                        "logo": "PHI",
                                        "attributes": {
                                            "Punkte pro Spiel": "28.06"
                                        }
                                    },
                                    "category": "Punkte pro Spiel",
                                    "turnWinner": "PLAYER",
                                    "playerCard": {
                                        "name": "Philadelphia Eagles",
                                        "logo": "PHI",
                                        "attributes": {
                                            "Punkte pro Spiel": "28.06"
                                        }
                                    },
                                    "opponentCard": {
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
    void getTurnResult_expectTurnDTO_withOpponentAsTurnWinner() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, loserDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Punkte pro Spiel"
                                }
                                """))
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

    @Test
    @DirtiesContext
    void getTurnResult_expectTurnDTO_whenItIsDraw() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, winnerDeck));

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Punkte pro Spiel"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                 {
                                    "score": {
                                        "opponent": 2,
                                        "player": 2
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
                                    "turnWinner": "DRAW",
                                    "playerCard": {
                                        "name": "Detroit Lions",
                                        "logo": "DET",
                                        "attributes": {
                                            "Punkte pro Spiel": "26.65"
                                        }
                                    },
                                    "opponentCard": {
                                        "name": "Detroit Lions",
                                        "logo": "DET",
                                        "attributes": {
                                            "Punkte pro Spiel": "26.65"
                                        }
                                    }
                                 }
                        """));
    }
}
