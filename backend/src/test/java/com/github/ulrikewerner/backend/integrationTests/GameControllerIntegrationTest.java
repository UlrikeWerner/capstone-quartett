package com.github.ulrikewerner.backend.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ulrikewerner.backend.entities.*;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private final Deck winnerDeckSmall = new Deck(List.of(detroit));
    private final Deck loserDeck = new Deck(List.of(philadelphia));
    private final Deck wrongCategoryDeck = new Deck(List.of(wrongCard));

    @Test
    @DirtiesContext
    void startNewGame_expectIdOfTheNewGame() throws Exception {
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
    void getOpenGames_expectEmptyList_whenNoGameIsOpen() throws Exception {
        Game game = new Game(winnerDeck, loserDeck);
        game.setFinished(true);
        gameRepo.save(game);

        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @Test
    @DirtiesContext
    void getOpenGames_expectListOfGames_whenGamesAreStillOpen() throws Exception {
        Game game1 = new Game(winnerDeck, loserDeck);
        Game game2 = new Game(winnerDeck, loserDeck);
        Game game3 = new Game(loserDeck, winnerDeck);
        game2.setFinished(true);
        gameRepo.save(game1);
        gameRepo.save(game2);
        gameRepo.save(game3);

        MvcResult result = mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Object> gameList = JsonPath.read(jsonResponse, "$[*]");

        for (Object game : gameList) {
            assertThat(Optional.ofNullable(JsonPath.read(game, "$.gameId"))).isNotNull();
            assertThat(Optional.ofNullable(JsonPath.read(game, "$.title"))).isNotNull();
        }
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
    void getTurnResult_expectThrowGameIsOverException() throws Exception {
        Game newGame = new Game(winnerDeck, loserDeck);
        newGame.setFinished(true);
        Game testGame = gameRepo.save(newGame);

        mockMvc.perform(put("/api/game/" + testGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "Touchdowns"
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Das Spiel ist vorbei!"));
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

    @Test
    @DirtiesContext
    void getTurnResult_expectFinalGameResultDTO_withOpponentAsWinner() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeckSmall, loserDeck));

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
                                        "player": 0
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
                                    },
                                    "winner": "OPPONENT",
                                    "finished": true
                                 }
                        """))
                .andExpect(jsonPath("$.nextTurnBy").doesNotExist())
                .andExpect(jsonPath("$.nextPlayerCard").doesNotExist());
    }

    @Test
    @DirtiesContext
    void getOpponentTurnResult_expectTurnDTO() throws Exception {
        CardAttribute attribute1Card1 = new CardAttribute("test1", 23, false, true);
        CardAttribute attribute1Card2 = new CardAttribute("test1", 13, false, true);
        CardAttribute attribute2Card1 = new CardAttribute("test2", 2323, true, false);
        CardAttribute attribute2Card2 = new CardAttribute("test2", 1323, true, false);
        Card card1 = new Card("1", "card1", NflLogoAcronym.DET, new ArrayList<>(List.of(attribute1Card1, attribute2Card1)));
        Card card2 = new Card("2", "card2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute1Card2, attribute2Card2)));
        Game newGame = new Game(new Deck(List.of(card1, card2)), new Deck(List.of(card2, card1)));
        newGame.setPlayerTurn(false);
        Game testGame = gameRepo.save(newGame);

        mockMvc.perform(get("/api/game/" + testGame.getId() + "/opponentTurn"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                 {
                                    "nextTurnBy": "PLAYER",
                                    "nextPlayerCard": {
                                        "name": "card2",
                                        "logo": "NO",
                                        "attributes": {
                                            "test1": "13",
                                            "test2": "13.23"
                                        }
                                    },
                                    "playerCard": {
                                        "name": "card1",
                                        "logo": "DET",
                                        "attributes": {
                                            "test1": "23",
                                            "test2": "23.23"
                                        }
                                    },
                                    "opponentCard": {
                                        "name": "card2",
                                        "logo": "NO",
                                        "attributes": {
                                            "test1": "13",
                                            "test2": "13.23"
                                        }
                                    }
                                 }
                        """))
                .andExpect(jsonPath("$.score.opponent", either(is(1)).or(is(2)).or(is(3))))
                .andExpect(jsonPath("$.score.player", either(is(1)).or(is(2)).or(is(3))))
                .andExpect(jsonPath("$.category", either(is("test1")).or(is("test2"))))
                .andExpect(jsonPath("$.turnWinner", either(is("PLAYER")).or(is("OPPONENT")).or(is("DRAW"))));
    }

    @Test
    @DirtiesContext
    void getOpponentTurnResult_ReturnFinalGameResultDTO() throws Exception {
        CardAttribute attribute1Card1 = new CardAttribute("test1", 23, false, true);
        CardAttribute attribute1Card2 = new CardAttribute("test1", 13, false, true);
        CardAttribute attribute2Card1 = new CardAttribute("test2", 2323, true, true);
        CardAttribute attribute2Card2 = new CardAttribute("test2", 1323, true, true);
        Card card1 = new Card("1", "card1", NflLogoAcronym.DET, new ArrayList<>(List.of(attribute1Card1, attribute2Card1)));
        Card card2 = new Card("2", "card2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute1Card2, attribute2Card2)));
        Game newGame = new Game(new Deck(List.of(card1)), new Deck(List.of(card2)));
        newGame.setPlayerTurn(false);
        Game testGame = gameRepo.save(newGame);

        mockMvc.perform(get("/api/game/" + testGame.getId() + "/opponentTurn"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                 {
                                    "score": {
                                    "opponent": 0,
                                    "player": 2
                                    },
                                    "playerCard": {
                                        "name": "card1",
                                        "logo": "DET",
                                        "attributes": {
                                            "test1": "23",
                                            "test2": "23.23"
                                        }
                                    },
                                    "opponentCard": {
                                        "name": "card2",
                                        "logo": "NO",
                                        "attributes": {
                                            "test1": "13",
                                            "test2": "13.23"
                                        }
                                    },
                                    "turnWinner": "PLAYER",
                                    "winner": "PLAYER",
                                    "finished": true
                                 }
                        """))
                .andExpect(jsonPath("$.category", either(is("test1")).or(is("test2"))))
                .andExpect(jsonPath("$.nextTurnBy").doesNotExist())
                .andExpect(jsonPath("$.nextPlayerCard").doesNotExist());
    }

    @Test
    @DirtiesContext
    void getOpponentTurnResult_expectThrowNotOpponentTurnException() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, loserDeck));

        mockMvc.perform(get("/api/game/" + testGame.getId() + "/opponentTurn"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Der Gegner ist nicht am Zug!"));
    }

    @Test
    @DirtiesContext
    void getOpponentTurnResult_expectThrowGameIsOverException() throws Exception {
        Game newGame = new Game(winnerDeck, loserDeck);
        newGame.setFinished(true);
        Game testGame = gameRepo.save(newGame);

        mockMvc.perform(get("/api/game/" + testGame.getId() + "/opponentTurn"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Das Spiel ist vorbei!"));
    }

    @Test
    @DirtiesContext
    void deleteGame_expectStatusOK() throws Exception {
        Game testGame = gameRepo.save(new Game(winnerDeck, loserDeck));
        String id = testGame.getId();

        mockMvc.perform(get("/api/game/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/game/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/game/" + id))
                .andExpect(status().isNotFound());
    }
}
