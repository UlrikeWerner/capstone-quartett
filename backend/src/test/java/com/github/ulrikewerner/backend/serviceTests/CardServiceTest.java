package com.github.ulrikewerner.backend.serviceTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import com.github.ulrikewerner.backend.entities.NflLogoAcronym;
import com.github.ulrikewerner.backend.entities.TurnWinner;
import com.github.ulrikewerner.backend.exception.CategoryNotFoundException;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import com.github.ulrikewerner.backend.services.CardService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    CardRepo cardRepo = mock(CardRepo.class);
    CardService cardService = new CardService(cardRepo);
    CardAttribute testAttribute = new CardAttribute("test", 2642, true, true);
    ArrayList<CardAttribute> testAttributes = new ArrayList<>(List.of(testAttribute));
    Card testCard = new Card("1", "Flensburger Seemöwen", NflLogoAcronym.NFL, testAttributes);

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

        CardAttribute cardAttribute = new CardAttribute("test", 2642, true, true);
        List<Card> expected = List.of(new Card("1", "Flensburger Seemöwen", NflLogoAcronym.NFL, new ArrayList<>(List.of(cardAttribute))));

        verify(cardRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void compare_expectTurnWinnerPlayer_WhenIsBiggerBetterIsTrueAndThePlayerCardIsHigher() throws CategoryNotFoundException {
        CardAttribute more = new CardAttribute("test", 12, true, true);
        CardAttribute more2 = new CardAttribute("test", 1212, false, true);
        CardAttribute less = new CardAttribute("test", 2, true, true);
        CardAttribute less2 = new CardAttribute("test", 212, false, true);
        Card moreCard = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more)));
        Card moreCard2 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more2)));
        Card lessCard = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less)));
        Card lessCard2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less2)));

        TurnWinner actual = cardService.compare(moreCard, lessCard, "test");
        TurnWinner actual2 = cardService.compare(moreCard2, lessCard2, "test");

        assertEquals(TurnWinner.PLAYER, actual);
        assertEquals(TurnWinner.PLAYER, actual2);
    }

    @Test
    void compare_expectTurnWinnerOpponent_WhenIsBiggerBetterIsTrueAndTheOpponentCardIsHigher() throws CategoryNotFoundException {
        CardAttribute more = new CardAttribute("test", 12, true, true);
        CardAttribute more2 = new CardAttribute("test", 1212, false, true);
        CardAttribute less = new CardAttribute("test", 2, true, true);
        CardAttribute less2 = new CardAttribute("test", 212, false, true);
        Card moreCard = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more)));
        Card moreCard2 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more2)));
        Card lessCard = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less)));
        Card lessCard2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less2)));

        TurnWinner actual = cardService.compare(lessCard, moreCard, "test");
        TurnWinner actual2 = cardService.compare(lessCard2, moreCard2, "test");

        assertEquals(TurnWinner.OPPONENT, actual);
        assertEquals(TurnWinner.OPPONENT, actual2);
    }

    @Test
    void compare_expectTurnWinnerDraw_WhenIsBiggerBetterIsTrueAndTheCardsAreEqual() throws CategoryNotFoundException {
        CardAttribute attribute = new CardAttribute("test", 12, true, true);
        CardAttribute attribute2 = new CardAttribute("test", 1212, false, true);
        Card cardWithAttribute1Version1 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(attribute)));
        Card cardWithAttribute2Version1 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(attribute2)));
        Card cardWithAttribute1Version2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute)));
        Card cardWithAttribute12Version2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute2)));

        TurnWinner actual = cardService.compare(cardWithAttribute1Version1, cardWithAttribute1Version2, "test");
        TurnWinner actual2 = cardService.compare(cardWithAttribute2Version1, cardWithAttribute12Version2, "test");

        assertEquals(TurnWinner.DRAW, actual);
        assertEquals(TurnWinner.DRAW, actual2);
    }
    @Test
    void compare_expectTurnWinnerPlayer_WhenIsBiggerBetterIsFalseAndThePlayerCardIsLess() throws CategoryNotFoundException {
        CardAttribute more = new CardAttribute("test", 12, true, false);
        CardAttribute more2 = new CardAttribute("test", 1212, false, false);
        CardAttribute less = new CardAttribute("test", 2, true, false);
        CardAttribute less2 = new CardAttribute("test", 212, false, false);
        Card moreCard = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more)));
        Card moreCard2 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more2)));
        Card lessCard = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less)));
        Card lessCard2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less2)));

        TurnWinner actual = cardService.compare(lessCard, moreCard, "test");
        TurnWinner actual2 = cardService.compare(lessCard2, moreCard2, "test");

        assertEquals(TurnWinner.PLAYER, actual);
        assertEquals(TurnWinner.PLAYER, actual2);
    }

    @Test
    void compare_expectTurnWinnerOpponent_WhenIsBiggerBetterIsFalseAndTheOpponentCardIsLess() throws CategoryNotFoundException {
        CardAttribute more = new CardAttribute("test", 12, true, false);
        CardAttribute more2 = new CardAttribute("test", 1212, false, false);
        CardAttribute less = new CardAttribute("test", 2, true, false);
        CardAttribute less2 = new CardAttribute("test", 212, false, false);
        Card moreCard = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more)));
        Card moreCard2 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(more2)));
        Card lessCard = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less)));
        Card lessCard2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(less2)));

        TurnWinner actual = cardService.compare(moreCard, lessCard, "test");
        TurnWinner actual2 = cardService.compare(moreCard2, lessCard2,"test");

        assertEquals(TurnWinner.OPPONENT, actual);
        assertEquals(TurnWinner.OPPONENT, actual2);
    }

    @Test
    void compare_expectTurnWinnerDraw_WhenIsBiggerBetterIsFalseAndTheCardsAreEqual() throws CategoryNotFoundException {
        CardAttribute attribute = new CardAttribute("test", 12, true, false);
        CardAttribute attribute2 = new CardAttribute("test", 1212, false, false);
        Card cardWithAttribute1Version1 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(attribute)));
        Card cardWithAttribute2Version1 = new Card("1", "testCard1", NflLogoAcronym.ARI, new ArrayList<>(List.of(attribute2)));
        Card cardWithAttribute1Version2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute)));
        Card cardWithAttribute12Version2 = new Card("2", "testCard2", NflLogoAcronym.NO, new ArrayList<>(List.of(attribute2)));

        TurnWinner actual = cardService.compare(cardWithAttribute1Version1, cardWithAttribute1Version2, "test");
        TurnWinner actual2 = cardService.compare(cardWithAttribute2Version1, cardWithAttribute12Version2, "test");

        assertEquals(TurnWinner.DRAW, actual);
        assertEquals(TurnWinner.DRAW, actual2);
    }

    @Test
    void getRandomAttribute_shouldReturnACardAttribute() throws CategoryNotFoundException {
        CardAttribute testAttribute = new CardAttribute("test", 2642, true, true);
        CardAttribute testAttribute2 = new CardAttribute("secondTest", 42, false, true);
        ArrayList<CardAttribute> testAttributes = new ArrayList<>(List.of(testAttribute, testAttribute2));
        Card testCard = new Card("1", "Flensburger Seemöwen", NflLogoAcronym.NFL, testAttributes);

        CardAttribute randomAttribute = cardService.getRandomAttribute(testCard);

        assertTrue(Objects.equals(randomAttribute.name(), "test") || Objects.equals(randomAttribute.name(), "secondTest"));
    }

    @Test
    void getRandomAttribute_shouldReturnDifferentCardAttribute() throws CategoryNotFoundException {
        CardAttribute testAttribute = new CardAttribute("test", 2642, true, true);
        CardAttribute testAttribute2 = new CardAttribute("test2", 2642, true, true);
        CardAttribute testAttribute3 = new CardAttribute("test3", 2642, true, true);
        CardAttribute testAttribute4 = new CardAttribute("test4", 2642, true, true);
        CardAttribute testAttribute5 = new CardAttribute("test5", 2642, true, true);
        ArrayList<CardAttribute> testAttributes = new ArrayList<>(List.of(testAttribute, testAttribute2, testAttribute3, testAttribute4, testAttribute5));
        Card testCard = new Card("1", "Flensburger Seemöwen", NflLogoAcronym.NFL, testAttributes);

        CardAttribute randomAttribute = cardService.getRandomAttribute(testCard);
        CardAttribute randomAttribute2 = cardService.getRandomAttribute(testCard);

        assertNotEquals(randomAttribute, randomAttribute2);
    }

    @Test
    void getRandomAttribute_shouldThrowCategoryNotFoundException() {
        ArrayList<CardAttribute> testAttributes = new ArrayList<>(List.of());
        Card testCard = new Card("1", "Flensburger Seemöwen", NflLogoAcronym.NFL, testAttributes);

        assertThrowsExactly(CategoryNotFoundException.class, () -> cardService.getRandomAttribute(testCard));
    }
}
