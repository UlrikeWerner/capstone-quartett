package com.github.ulrikewerner.backend.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class Deck {
    private ArrayList<Card> cards;

    public Deck(List<Card> cardList){
        cards = new ArrayList<>(cardList);
    }

    public int size() {
        return cards.size();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Optional<Card> lookAtFirstCard() {
        if(cards.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(cards.get(0));
    }

    public Optional<Card> drawFirstCard() throws IndexOutOfBoundsException{
        if(cards.isEmpty()){
            return Optional.empty();
        }
        Card card = cards.get(0);
        cards.remove(0);
        return Optional.of(card);
    }
}
