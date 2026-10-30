package com.jaimemoreno.gameshelf.application.game.port.out;

import com.jaimemoreno.gameshelf.domain.game.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Game save(Game game);

    Optional<Game> findById(Long id);

    List<Game> findAll();
}
