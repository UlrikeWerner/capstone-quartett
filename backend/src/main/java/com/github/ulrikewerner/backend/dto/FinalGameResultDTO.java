package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.GameWinner;
import com.github.ulrikewerner.backend.entities.TurnWinner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalGameResultDTO extends BaseGameStateDTO{
    private String category;
    private TurnWinner turnWinner;
    private CardDTO playerCard;
    private CardDTO opponentCard;
    private GameWinner winner;

    public FinalGameResultDTO(Game game) {
        super(game);
    }

    public FinalGameResultDTO(Game game, String category, TurnWinner turnWinner, Card playerCard, Card opponentCard, GameWinner gameWinner) {
        super(game);
        this.category = category;
        this.turnWinner = turnWinner;
        this.playerCard = new CardDTO(playerCard);
        this.opponentCard = new CardDTO(opponentCard);
        this.winner = gameWinner;
    }
}
