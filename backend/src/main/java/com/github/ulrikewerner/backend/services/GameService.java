package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.dto.TurnDTO;
import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.Deck;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.TurnWinner;
import com.github.ulrikewerner.backend.exception.CategoryNotFoundException;
import com.github.ulrikewerner.backend.exception.GameNotFoundException;
import com.github.ulrikewerner.backend.exception.NotYourTurnException;
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

    public Optional<Game> getGameById(String id) {
        return gameRepo.findById(id);
    }

    public TurnDTO getPlayerTurnResult(String gameId, String category)
            throws GameNotFoundException, CategoryNotFoundException, NotYourTurnException {
        Game currentGame = getGameById(gameId).orElseThrow(()-> new GameNotFoundException(gameId));
        if(!currentGame.isPlayerTurn()){
            throw new NotYourTurnException();
        }
        Card playerCard = currentGame.getPlayerCards().drawFirstCard().orElseThrow();
        Card opponentCard = currentGame.getOpponentCards().drawFirstCard().orElseThrow();
        TurnWinner winner = cardService.compare(playerCard, opponentCard, category);
        switch (winner) {
            case DRAW -> {
                currentGame.getPlayerCards().addCardToBottom(playerCard);
                currentGame.getOpponentCards().addCardToBottom(opponentCard);
            }
            case PLAYER -> {
                currentGame.getPlayerCards().addCardToBottom(playerCard);
                currentGame.getPlayerCards().addCardToBottom(opponentCard);
            }
            case OPPONENT -> {
                currentGame.getOpponentCards().addCardToBottom(opponentCard);
                currentGame.getOpponentCards().addCardToBottom(playerCard);
            }
        }
        currentGame.setPlayerTurn(false);
        Game savedGame = gameRepo.save(currentGame);
        return new TurnDTO(savedGame, category, winner, playerCard, opponentCard);
    }
}
