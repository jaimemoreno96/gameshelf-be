package com.jaimemoreno.gameshelf.application.game.service;

import com.jaimemoreno.gameshelf.application.game.command.SearchGamesCommand;
import com.jaimemoreno.gameshelf.application.game.port.in.SearchGamesUseCase;
import com.jaimemoreno.gameshelf.application.game.port.out.GameSearchPort;
import com.jaimemoreno.gameshelf.domain.game.GameSearchResult;

import java.util.List;

public final class GameSearchService implements SearchGamesUseCase {

    private final GameSearchPort gameSearchPort;

    public GameSearchService(GameSearchPort gameSearchPort) {
        this.gameSearchPort = gameSearchPort;
    }

    @Override
    public List<GameSearchResult> search(SearchGamesCommand command) {
        if (command == null || command.query() == null || command.query().isBlank()) {
            throw new IllegalArgumentException("query must not be blank");
        }
        return gameSearchPort.search(command.query(), command.limit());
    }
}
