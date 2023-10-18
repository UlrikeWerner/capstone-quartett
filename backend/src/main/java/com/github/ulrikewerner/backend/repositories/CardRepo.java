package com.github.ulrikewerner.backend.repositories;

import com.github.ulrikewerner.backend.entities.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends MongoRepository<Card, String> {
}
