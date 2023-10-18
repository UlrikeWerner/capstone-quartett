package com.github.ulrikewerner.backend.repositories;

import com.github.ulrikewerner.backend.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends MongoRepository<Game, String> {
}
