package com.jaimemoreno.gameshelf.application.game.port.out;

import com.jaimemoreno.gameshelf.domain.game.GameSearchResult;

import java.util.List;

public interface GameSearchPort {

    List<GameSearchResult> search(String query, int limit);
}
