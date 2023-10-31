package com.github.ulrikewerner.backend.entityTests;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.NflLogoAcronym;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    CardAttribute dummyCardAttribute1 = new CardAttribute("test", 2542, true, true);
    CardAttribute dummyCardAttribute2 = new CardAttribute("test2", 42, false, false);
    Card dummyCard1 = new Card("1", "Flensburg Seemöwen", NflLogoAcronym.BAL, new ArrayList<>(List.of(dummyCardAttribute1)));
    Card dummyCard2 = new Card("2", "Kieler Seemuscheln", NflLogoAcronym.ATL, new ArrayList<>(List.of(dummyCardAttribute1)));
    Card dummyCard3 = new Card("3", "Hamburg SeeTeufel", NflLogoAcronym.ARI, new ArrayList<>(List.of(dummyCardAttribute1)));
    Card dummyCard4 = new Card("4", "Lübecker MarzipanSeesterne", NflLogoAcronym.CHI , new ArrayList<>(List.of(dummyCardAttribute1)));
    Card dummyCard5 = new Card("5", "Holnis SeeZunge", NflLogoAcronym.BUF, new ArrayList<>(List.of(dummyCardAttribute1)));
    Card dummyCard6 = new Card("6", "Flensburger Meerjungfrauen", NflLogoAcronym.NFL, new ArrayList<>(List.of(dummyCardAttribute2)));
    Card dummyCard7 = new Card("7", "Kieler FischKöpfe", NflLogoAcronym.JAX, new ArrayList<>(List.of(dummyCardAttribute2)));
    Card dummyCard8 = new Card("8", "Lübecker Piraten", NflLogoAcronym.TEN, new ArrayList<>(List.of(dummyCardAttribute2)));
    Card dummyCard9 = new Card("9", "Hamburger GewürzHändler", NflLogoAcronym.SEA, new ArrayList<>(List.of(dummyCardAttribute2)));
    Card dummyCard10 = new Card("10", "Holnis Strandläufer", NflLogoAcronym.NO, new ArrayList<>(List.of(dummyCardAttribute2)));

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
        List<Card> cardList = List.of(
                dummyCard1, dummyCard2, dummyCard3, dummyCard4, dummyCard5, dummyCard6, dummyCard7, dummyCard8, dummyCard9, dummyCard10
        );
        List<Card> sortedList = List.of(
                dummyCard1, dummyCard2, dummyCard3, dummyCard4, dummyCard5, dummyCard6, dummyCard7, dummyCard8, dummyCard9, dummyCard10
        );

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

    @Test
    void Deck_addCardToBottom_shouldAddCardOneTheRightPosition() {
        List<Card> cardList = List.of(dummyCard1);
        Deck cardDeck = new Deck(cardList);

        assertEquals(1, cardDeck.size());

        cardDeck.addCardToBottom(dummyCard2);

        assertEquals(2, cardDeck.size());
        assertEquals(dummyCard2, cardDeck.getCards().get(cardDeck.size()-1));
    }
}
