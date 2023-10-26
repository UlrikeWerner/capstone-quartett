package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import com.github.ulrikewerner.backend.services.CardService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    CardRepo cardRepo = mock(CardRepo.class);
    CardService cardService = new CardService(cardRepo);
    CardAttribute testAttribute = new CardAttribute("test", 2642, true);
    ArrayList<CardAttribute> testAttributes = new ArrayList<>(List.of(testAttribute));
    Card testCard = new Card("1", "Flensburger Seemöwen", testAttributes);

    @Test
    void getAllCard_expectEmptyList() {

        when(cardRepo.findAll()).thenReturn(List.of());
        List<Card> actual = cardService.getAllCards();

        verify(cardRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllCard_expectOneCard() {

        List<Card> cardList = List.of(testCard);

        when(cardRepo.findAll()).thenReturn(cardList);
        List<Card> actual = cardService.getAllCards();

        CardAttribute cardAttribute = new CardAttribute("test", 2642, true);
        List<Card> expected = List.of(new Card("1", "Flensburger Seemöwen", new ArrayList<>(List.of(cardAttribute))));

        verify(cardRepo).findAll();
        assertEquals(expected, actual);
    }
}
