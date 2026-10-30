package com.jaimemoreno.gameshelf.application.game.port.in;

import com.jaimemoreno.gameshelf.application.game.command.CreateGameCommand;
import com.jaimemoreno.gameshelf.domain.game.Game;

public interface CreateGameUseCase {
    Game create(CreateGameCommand command);
}
