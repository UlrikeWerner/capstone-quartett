package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final CardService cardService;
    private final GameRepo gameRepo;
    public String startNewGame() {
        Deck cardDeck = new Deck(cardService.getAllCards());
        cardDeck.shuffleDeck();

        List<Card> playerCards = new ArrayList<>();
        List<Card> opponentCards = new ArrayList<>();
        while (cardDeck.size() > 1){
            playerCards.add(cardDeck.drawFirstCard());
            opponentCards.add(cardDeck.drawFirstCard());
        }

        Game newGame = gameRepo.save(new Game(new Deck(playerCards), new Deck(opponentCards)));
        return newGame.getId();
    }
}
