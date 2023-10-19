package com.github.ulrikewerner.backend.services;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.repositories.CardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;

    public ArrayList<Card> getAllCards() {
        return (ArrayList<Card>) cardRepo.findAll();
    }
}
