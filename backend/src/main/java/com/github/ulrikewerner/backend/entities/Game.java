package com.github.ulrikewerner.backend.entities;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Game {
    private String id;
    private boolean isPlayerTurn;
    private Deck playerCards;
    private Deck opponentCards;
    private boolean isFinished;

    public Game(Deck playerCards, Deck opponentCards) {
        isPlayerTurn = true;
        this.playerCards = playerCards;
        this.opponentCards = opponentCards;
        isFinished = false;
    }
}
