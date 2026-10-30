package com.jaimemoreno.gameshelf.application.game.service;

import com.jaimemoreno.gameshelf.application.game.command.CreateGameCommand;
import com.jaimemoreno.gameshelf.application.game.port.in.CreateGameUseCase;
import com.jaimemoreno.gameshelf.application.game.port.in.FindGamesUseCase;
import com.jaimemoreno.gameshelf.application.game.port.out.GameRepository;
import com.jaimemoreno.gameshelf.domain.game.Game;

import java.util.List;
import java.util.Optional;

public class GameService implements CreateGameUseCase, FindGamesUseCase {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game create(CreateGameCommand command) {
        Game game = new Game(
                null,
                command.title(),
                command.description(),
                command.coverImageUrl(),
                command.releaseDate());
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }
}
