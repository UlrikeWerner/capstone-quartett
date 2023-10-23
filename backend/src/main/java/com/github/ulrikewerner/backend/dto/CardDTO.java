package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Card;
import lombok.Getter;

@Getter
public class CardDTO {
    private final String team;
    private final double pointsPerGame;

    public CardDTO(Card card) {
        team = card.team();
        pointsPerGame = (double) card.pointsPerGame() / 100;
    }

}
