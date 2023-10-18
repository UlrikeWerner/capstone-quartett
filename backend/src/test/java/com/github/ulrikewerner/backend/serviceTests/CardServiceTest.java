package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import com.github.ulrikewerner.backend.services.CardService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    CardRepo cardRepo = mock(CardRepo.class);
    CardService cardService = new CardService(cardRepo);

    Card testCard = new Card("1", "Flensburger Seemöwen", 26.42f);

    @Test
    void getAllCard_expectEmptyList() {

        List<Card> cardList = List.of();

        when(cardRepo.findAll()).thenReturn(cardList);
        List<Card> actual = cardService.getAllCards();

        List<Card> expected = List.of();

        verify(cardRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAllCard_expectOneCard() {

        List<Card> cardList = List.of(testCard);

        when(cardRepo.findAll()).thenReturn(cardList);
        List<Card> actual = cardService.getAllCards();

        List<Card> expected = List.of(new Card(
                "1", "Flensburger Seemöwen", 26.42f
        ));

        verify(cardRepo).findAll();
        assertEquals(expected, actual);
    }
}
