package com.github.ulrikewerner.backend.entities;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class Deck {
    private List<Card> cards;

    public int size() {
        return cards.size();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawFirstCard() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }
}
