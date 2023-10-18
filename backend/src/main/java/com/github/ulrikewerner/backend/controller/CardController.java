package com.github.ulrikewerner.backend.controller;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/all")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

}
