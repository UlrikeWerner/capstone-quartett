package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.TurnWinner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurnDTO extends GameStateDTO{
    private String category;
    private TurnWinner turnWinner;
    private Card playerCard;
    private Card opponentCard;

    public TurnDTO(Game game) {
        super(game);
    }

    public TurnDTO(Game game, String category, TurnWinner turnWinner, Card playerCard, Card opponentCard) {
        super(game);
        this.category = category;
        this.turnWinner = turnWinner;
        this.playerCard = playerCard;
        this.opponentCard = opponentCard;
    }
}
