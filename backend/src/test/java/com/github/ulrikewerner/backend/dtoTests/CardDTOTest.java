package com.github.ulrikewerner.backend.dtoTests;

import com.github.ulrikewerner.backend.dto.CardDTO;
import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import com.github.ulrikewerner.backend.entities.NflLogoAcronym;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDTOTest {
    private final CardAttribute testAttribute = new CardAttribute("test", 2542, true, true);
    private final Card flensburgCard = new Card("1", "Flensburg Seemöwen", NflLogoAcronym.NFL, new ArrayList<>(List.of(testAttribute)));
    private final CardAttribute testAttribute2 = new CardAttribute("test", 42, false, false);
    private final Card kielerCard = new Card("1", "Kieler SeeTeufel", NflLogoAcronym.ARI, new ArrayList<>(List.of(testAttribute2)));

    @Test
    void CardDTO_constructor_createCorrectObjectWithDecimalValue(){

        CardDTO testCardDTO = new CardDTO(flensburgCard);

        assertEquals(flensburgCard.name(), testCardDTO.getName());
        assertEquals(flensburgCard.logo(), testCardDTO.getLogo());
        assertTrue(testCardDTO.getAttributes().containsKey("test"));
        assertEquals("25.42", testCardDTO.getAttributes().get("test"));
    }

    @Test
    void CardDTO_constructor_createCorrectObjectValue(){

        CardDTO testCardDTO = new CardDTO(kielerCard);

        assertEquals(kielerCard.name(), testCardDTO.getName());
        assertEquals(kielerCard.logo(), testCardDTO.getLogo());
        assertTrue(testCardDTO.getAttributes().containsKey("test"));
        assertEquals("42", testCardDTO.getAttributes().get("test"));
    }
}
