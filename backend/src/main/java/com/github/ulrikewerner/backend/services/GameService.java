package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final CardService cardService;
    private final GameRepo gameRepo;
    public Game startNewGame() {
        Deck cardDeck = new Deck(cardService.getAllCards());
        cardDeck.shuffleDeck();

        ArrayList<Card> playerCards = new ArrayList<>();
        ArrayList<Card> opponentCards = new ArrayList<>();
        while (cardDeck.size() > 1){
            Optional<Card> optionalCard1 = cardDeck.drawFirstCard();
            Optional<Card> optionalCard2 = cardDeck.drawFirstCard();

            optionalCard1.ifPresent(playerCards::add);
            optionalCard2.ifPresent(opponentCards::add);
        }

        return gameRepo.save(new Game(new Deck(playerCards), new Deck(opponentCards)));
    }
}
