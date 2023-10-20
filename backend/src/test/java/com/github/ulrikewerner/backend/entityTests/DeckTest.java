package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Card dummyCard1 = new Card("1", "Flensburg Seemöwen", 25.42f);
    Card dummyCard2 = new Card("2", "Kieler Seemuscheln", 23.25f);
    Card dummyCard3 = new Card("3", "Hamburg SeeTeufel", 26.23f);
    Card dummyCard4 = new Card("4", "Lübecker MarzipanSchweine", 24.12f);

    @Test
    void Deck_size_when2CardsAreInTheDeck_theSizeShouldBe2() {
        List<Card> cardList = List.of(dummyCard1, dummyCard2);
        Deck cardDeck = new Deck(cardList);

        int actual = cardDeck.size();
        int expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    void Deck_shuffleDeck_expectedUnsortedDeck() {
        List<Card> cardList = List.of(dummyCard1, dummyCard2, dummyCard3, dummyCard4);
        List<Card> sortedList = List.of(dummyCard1, dummyCard2, dummyCard3, dummyCard4);

        Deck cardDeck = new Deck(cardList);
        Deck sortedDeck = new Deck(sortedList);
        sortedDeck.shuffleDeck();

        assertNotEquals(cardDeck, sortedDeck);
    }

    @Test
    void Deck_shuffleDeck_expectedTwoShuffledDecksAreNotTheSame() {
        List<Card> cardList = List.of(dummyCard1, dummyCard2, dummyCard3, dummyCard4);
        List<Card> cardList2 = List.of(dummyCard1, dummyCard2, dummyCard3, dummyCard4);

        Deck cardDeck1 = new Deck(cardList);
        Deck cartDeck2 = new Deck(cardList2);

        cardDeck1.shuffleDeck();
        cartDeck2.shuffleDeck();

        assertNotEquals(cardDeck1, cartDeck2);
    }

    @Test
    void Deck_lookAtFirstCard_expectedReturnFirstCardOfTheDeck() {
        List<Card> cardList = List.of(dummyCard1, dummyCard2);
        Deck cardDeck = new Deck(cardList);

        Optional<Card> actual = cardDeck.lookAtFirstCard();
        Card expect = dummyCard1;

        assertEquals(expect, actual.orElse(null));
    }

    @Test
    void Deck_lookAtFirstCard_expectedReturnEmptyOptional() {
        List<Card> cardList = List.of();
        Deck cardDeck = new Deck(cardList);

        Optional<Card> actual = cardDeck.lookAtFirstCard();

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void Deck_drawFirstCard_expectedReturnRightCardAndDeleteIt() {
        List<Card> cardList = List.of(dummyCard1, dummyCard2);
        Deck cardDeck1 = new Deck(cardList);

        List<Card> cardList2 = List.of(dummyCard1, dummyCard2);
        Deck cardDeckOriginalValue = new Deck(cardList2);

        Optional<Card> optionalCard = cardDeck1.drawFirstCard();
        Card firstCard = optionalCard.orElse(null);

        assertNotEquals(cardDeck1, cardDeckOriginalValue);
        assertNotNull(firstCard);
        assertEquals(firstCard, cardDeckOriginalValue.lookAtFirstCard().orElse(null));
    }

    @Test
    void Deck_drawFirstCard_expectedEmptyOptional_WhenTheDeckIsEmpty() {
        List<Card> cardList = List.of();
        Deck cardDeck1 = new Deck(cardList);

        Optional<Card> optionalCard = cardDeck1.drawFirstCard();

        assertEquals(Optional.empty(), optionalCard);
    }
}