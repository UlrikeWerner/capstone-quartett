package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Card c1 = new Card("1", "Flensburg Seemöwen", 25.42f);
    Card c2 = new Card("2", "Kieler Seemuscheln", 23.25f);
    Card c3 = new Card("3", "Hamburg SeeTeufel", 26.23f);
    Card c4 = new Card("4", "Lübecker MarzipanSchweine", 24.12f);

    @Test
    void Deck_size_when2CardsAreInTheDeck_theSizeShouldBe2() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(c1);
        cardList.add(c2);
        Deck cardDeck = new Deck(cardList);

        int actual = cardDeck.size();
        int expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    void Deck_shuffleDeck_expectedUnsortedDeck() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(c1);
        cardList.add(c2);
        cardList.add(c3);
        cardList.add(c4);

        ArrayList<Card> sortedList = new ArrayList<>();
        sortedList.add(c1);
        sortedList.add(c2);
        sortedList.add(c3);
        sortedList.add(c4);

        Deck cardDeck = new Deck(cardList);
        Deck sortedDeck = new Deck(sortedList);
        sortedDeck.shuffleDeck();

        assertNotEquals(cardDeck, sortedDeck);
    }

    @Test
    void Deck_shuffleDeck_expectedTwoShuffledDecksAreNotTheSame() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(c1);
        cardList.add(c2);
        cardList.add(c3);
        cardList.add(c4);

        ArrayList<Card> cardList2 = new ArrayList<>();
        cardList2.add(c1);
        cardList2.add(c2);
        cardList2.add(c3);
        cardList2.add(c4);

        Deck cardDeck1 = new Deck(cardList);
        Deck cartDeck2 = new Deck(cardList2);

        cardDeck1.shuffleDeck();
        cartDeck2.shuffleDeck();

        assertNotEquals(cardDeck1, cartDeck2);
    }

    @Test
    void Deck_lookAtFirstCard_expectedReturnFirstCardOfTheDeck() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(c1);
        cardList.add(c2);
        Deck cardDeck = new Deck(cardList);

        Optional<Card> actual = cardDeck.lookAtFirstCard();
        Card expect = c1;

        assertEquals(expect, actual.orElse(null));
    }

    @Test
    void Deck_lookAtFirstCard_expectedReturnEmptyOptional() {
        ArrayList<Card> cardList = new ArrayList<>();
        Deck cardDeck = new Deck(cardList);

        Optional<Card> actual = cardDeck.lookAtFirstCard();

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void Deck_drawFirstCard_expectedReturnRightCardAndDeleteIt() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(c1);
        cardList.add(c2);
        Deck cardDeck1 = new Deck(cardList);

        ArrayList<Card> cardList2 = new ArrayList<>();
        cardList2.add(c1);
        cardList2.add(c2);
        Deck cardDeckOriginalValue = new Deck(cardList2);

        Optional<Card> optionalCard = cardDeck1.drawFirstCard();
        Card firstCard = optionalCard.orElse(null);

        assertNotEquals(cardDeck1, cardDeckOriginalValue);
        assertEquals(firstCard, cardDeckOriginalValue.lookAtFirstCard().orElse(null));
    }

    @Test
    void Deck_drawFirstCard_expectedEmptyOptional_WhenTheDeckIsEmpty() {
        ArrayList<Card> cardList = new ArrayList<>();
        Deck cardDeck1 = new Deck(cardList);

        Optional<Card> optionalCard = cardDeck1.drawFirstCard();

        assertEquals(Optional.empty(), optionalCard);
    }
}
