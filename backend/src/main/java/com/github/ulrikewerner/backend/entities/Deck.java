package com.github.ulrikewerner.backend.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Card getNextCard() {
        return cards.get(0);
    }

    public void addCardToEndOfDeck(Card card) {
        cards.add(card);
    }

    public void removeFirstCard() {
        cards.remove(0);
    }
}
