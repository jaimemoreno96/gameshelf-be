package com.jaimemoreno.gameshelf.adapter.out.persistence.game;

import com.jaimemoreno.gameshelf.application.game.port.out.GameRepository;
import com.jaimemoreno.gameshelf.domain.game.Game;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GamePersistenceAdapter implements GameRepository {

    private final GameJpaRepository repository;

    public GamePersistenceAdapter(GameJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Game save(Game game) {
        return toDomain(repository.save(toEntity(game)));
    }

    @Override
    public Optional<Game> findById(Long id) {
        return repository.findById(id).map(GamePersistenceAdapter::toDomain);
    }

    @Override
    public List<Game> findAll() {
        return repository.findAll().stream()
                .map(GamePersistenceAdapter::toDomain)
                .toList();
    }

    private static GameJpaEntity toEntity(Game game) {
        return new GameJpaEntity(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getCoverImageUrl(),
                game.getReleaseDate());
    }

    private static Game toDomain(GameJpaEntity entity) {
        return new Game(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCoverImageUrl(),
                entity.getReleaseDate());
    }
}
