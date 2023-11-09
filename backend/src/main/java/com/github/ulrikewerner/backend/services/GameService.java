package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.dto.FinalGameResultDTO;
import com.github.ulrikewerner.backend.dto.TurnDTO;
import com.github.ulrikewerner.backend.entities.*;
import com.github.ulrikewerner.backend.exception.*;
import com.github.ulrikewerner.backend.interfaces.GameTurn;
import com.github.ulrikewerner.backend.repositories.GameRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        while (cardDeck.size() > 1) {
            Optional<Card> optionalCard1 = cardDeck.drawFirstCard();
            Optional<Card> optionalCard2 = cardDeck.drawFirstCard();

            optionalCard1.ifPresent(playerCards::add);
            optionalCard2.ifPresent(opponentCards::add);
        }

        return gameRepo.save(new Game(new Deck(playerCards), new Deck(opponentCards)));
    }

    public List<Game> getOpenGames() {
        List<Game> games = gameRepo.findAll()
                .stream()
                .filter(game -> !game.isFinished())
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toList());
        Collections.reverse(games);
        return games;
    }

    public Optional<Game> getGameById(String id) {
        return gameRepo.findById(id);
    }

    public void updateGameTitle(String gameId, String title) throws GameNotFoundException, TitleIsEmptyException {
        Game currentGame = getGameById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        if(title == null || title.isBlank()) {
            throw new TitleIsEmptyException();
        }
        currentGame.setTitle(title);
        gameRepo.save(currentGame);
    }

    public GameTurn getOpponentTurnResult(String gameId) throws CategoryNotFoundException, GameNotFoundException, NotOpponentTurnException, GameIsOverException {
        Game currentGame = getGameById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        if (currentGame.isFinished()) {
            throw new GameIsOverException();
        }
        if (currentGame.isPlayerTurn()) {
            throw new NotOpponentTurnException();
        }
        CardAttribute attribute = cardService.getRandomAttribute(currentGame.getOpponentCards().lookAtFirstCard().orElseThrow());
        return getTurnResult(currentGame, attribute.name());
    }

    public GameTurn getPlayerTurnResult(String gameId, String category)
            throws GameNotFoundException, CategoryNotFoundException, NotYourTurnException, GameIsOverException {

        Game currentGame = getGameById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        if (currentGame.isFinished()) {
            throw new GameIsOverException();
        }
        if (!currentGame.isPlayerTurn()) {
            throw new NotYourTurnException();
        }
        return getTurnResult(currentGame, category);
    }

    private GameTurn getTurnResult(Game game, String category) throws CategoryNotFoundException {
        Card playerCard = game.getPlayerCards().drawFirstCard().orElseThrow();
        Card opponentCard = game.getOpponentCards().drawFirstCard().orElseThrow();
        TurnWinner winner = cardService.compare(playerCard, opponentCard, category);
        switch (winner) {
            case DRAW -> {
                game.getPlayerCards().addCardToBottom(playerCard);
                game.getOpponentCards().addCardToBottom(opponentCard);
            }
            case PLAYER -> {
                game.getPlayerCards().addCardToBottom(playerCard);
                game.getPlayerCards().addCardToBottom(opponentCard);
            }
            case OPPONENT -> {
                game.getOpponentCards().addCardToBottom(opponentCard);
                game.getOpponentCards().addCardToBottom(playerCard);
            }
        }

        if (game.getPlayerCards().isEmpty() || game.getOpponentCards().isEmpty()) {
            game.setFinished(true);
        }
        game.setPlayerTurn(!game.isPlayerTurn());
        Game savedGame = gameRepo.save(game);
        if (game.getPlayerCards().isEmpty()) {
            return new FinalGameResultDTO(savedGame, category, winner, playerCard, opponentCard, GameWinner.OPPONENT);
        }
        if (game.getOpponentCards().isEmpty()) {
            return new FinalGameResultDTO(savedGame, category, winner, playerCard, opponentCard, GameWinner.PLAYER);
        }
        return new TurnDTO(savedGame, category, winner, playerCard, opponentCard);
    }

    public void deleteGame(String id) {
        gameRepo.deleteById(id);
    }
}
