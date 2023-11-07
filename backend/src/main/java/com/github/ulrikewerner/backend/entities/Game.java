package com.github.ulrikewerner.backend.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Game {
    private String id;
    private LocalDateTime date;
    private String title;
    private boolean isPlayerTurn;
    private Deck playerCards;
    private Deck opponentCards;
    private boolean isFinished;

    public Game(Deck playerCards, Deck opponentCards) {
        date = LocalDateTime.now();
        DateTimeFormatter dateFormatObject = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm");
        String formattedDate = date.format(dateFormatObject);
        title = "Spiel vom " + formattedDate;
        isPlayerTurn = true;
        this.playerCards = playerCards;
        this.opponentCards = opponentCards;
        isFinished = false;
    }
}
