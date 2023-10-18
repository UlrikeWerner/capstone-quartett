package com.github.ulrikewerner.backend.integrationTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CardRepo cardRepo;

    @Test
    @DirtiesContext
    void getAllCard_expectEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/card/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        []
                        """));
    }

    @Test
    @DirtiesContext
    void getAllCard_expectListOfCards() throws Exception {

        cardRepo.save(new Card("1", "Hamburg Neue Fische", 26.26f));
        cardRepo.save(new Card("2", "Flensburger Seemöwen", 26.42f));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/card/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("""
                                [
                                    {
                                        "id": "1",
                                        "team": "Hamburg Neue Fische",
                                        "pointsPerGame": 26.26
                                    },
                                    {
                                        "id": "2",
                                        "team": "Flensburger Seemöwen",
                                        "pointsPerGame": 26.42
                                    }
                                ]
                                """));
    }
}
