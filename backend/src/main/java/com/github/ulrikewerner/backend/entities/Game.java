package com.github.ulrikewerner.backend.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Game(Deck player, Deck opponent) {
        isPlayerTurn = true;
        playerCards = player;
        opponentCards = opponent;
        isFinished = false;
    }
}
