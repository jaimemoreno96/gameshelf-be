package com.jaimemoreno.gameshelf.application.game.port.in;

import com.jaimemoreno.gameshelf.domain.game.Game;

import java.util.List;
import java.util.Optional;

public interface FindGamesUseCase {
    List<Game> findAll();

    Optional<Game> findById(Long id);
}
