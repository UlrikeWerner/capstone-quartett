package com.github.ulrikewerner.backend.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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
        System.out.println(actual);
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
        Card kansas = new Card("1", "Kansas City Chiefs", 2918);
        Card philadelphia = new Card("2", "Philadelphia Eagles", 2806);
        Card detroit = new Card("1", "Detroit Lions", 2665);
        Deck playerDeck = new Deck(List.of(detroit, kansas));
        Deck opponentDeck = new Deck(List.of(philadelphia));
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
                                "team": "Detroit Lions",
                                "pointsPerGame": 26.65
                            }
                        }
                        """));
    }
}
