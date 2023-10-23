package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.CardDTO;
import com.github.ulrikewerner.backend.entities.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDTOTest {
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", 2542);

    @Test
    void CardDTO_constructor_createCorrectObject(){

        CardDTO testCardDTO = new CardDTO(flensburgCard);

        assertEquals(flensburgCard.team(), testCardDTO.getTeam());
        assertEquals(25.42, testCardDTO.getPointsPerGame());
    }
}
