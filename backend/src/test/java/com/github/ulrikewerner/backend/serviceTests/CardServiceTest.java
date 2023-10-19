package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import com.github.ulrikewerner.backend.services.CardService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    CardRepo cardRepo = mock(CardRepo.class);
    CardService cardService = new CardService(cardRepo);

    Card testCard = new Card("1", "Flensburger Seemöwen", 26.42f);

    @Test
    void getAllCard_expectEmptyList() {

        ArrayList<Card> cardList = new ArrayList<>();

        when(cardRepo.findAll()).thenReturn(cardList);
        ArrayList<Card> actual = cardService.getAllCards();

        verify(cardRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllCard_expectOneCard() {

        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(testCard);

        when(cardRepo.findAll()).thenReturn(cardList);
        ArrayList<Card> actual = cardService.getAllCards();

        ArrayList<Card> expected = new ArrayList<>();
        expected.add(new Card("1", "Flensburger Seemöwen", 26.42f));

        verify(cardRepo).findAll();
        assertEquals(expected, actual);
    }
}
