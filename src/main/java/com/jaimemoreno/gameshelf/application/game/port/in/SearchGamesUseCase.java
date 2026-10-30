package com.jaimemoreno.gameshelf.application.game.port.in;

import com.jaimemoreno.gameshelf.application.game.command.SearchGamesCommand;
import com.jaimemoreno.gameshelf.domain.game.GameSearchResult;

import java.util.List;

public interface SearchGamesUseCase {

    List<GameSearchResult> search(SearchGamesCommand command);
}
