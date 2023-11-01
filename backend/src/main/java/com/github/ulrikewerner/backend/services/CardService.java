package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import com.github.ulrikewerner.backend.entities.TurnWinner;
import com.github.ulrikewerner.backend.exception.CategoryNotFoundException;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;

    public List<Card> getAllCards() {
        return cardRepo.findAll();
    }

    public TurnWinner compare(Card playerCard, Card opponentCard, String category) throws CategoryNotFoundException {
        if(category.isBlank() ||
                playerCard.attributes().stream().noneMatch(attribute -> category.equals(attribute.name())) ||
                opponentCard.attributes().stream().noneMatch(attribute -> category.equals(attribute.name()))
        ) {
            throw new CategoryNotFoundException(category);
        }

        CardAttribute playerCardAttribute = playerCard.attributes()
                .stream()
                .filter(attribute -> category.equals(attribute.name()))
                .toList()
                .get(0);
        CardAttribute opponentCardAttribute = opponentCard.attributes()
                .stream()
                .filter(attribute -> category.equals(attribute.name()))
                .toList()
                .get(0);

        if(playerCardAttribute.value() > opponentCardAttribute.value()){
            return playerCardAttribute.isBiggerBetter() ? TurnWinner.PLAYER : TurnWinner.OPPONENT;
        }
        if(playerCardAttribute.value() < opponentCardAttribute.value()){
            return opponentCardAttribute.isBiggerBetter() ? TurnWinner.OPPONENT : TurnWinner.PLAYER;
        }
        return TurnWinner.DRAW;
    }

    public CardAttribute getRandomAttribute(Card card) throws CategoryNotFoundException {
        ArrayList<CardAttribute> attributeList = card.attributes();
        if (attributeList.isEmpty()) {
            throw new CategoryNotFoundException("");
        }
        int randomCategoryIndex = ThreadLocalRandom.current().nextInt(0, attributeList.size());
        return attributeList.get(randomCategoryIndex);
    }
}
