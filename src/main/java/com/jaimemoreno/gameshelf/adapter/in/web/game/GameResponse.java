package com.jaimemoreno.gameshelf.adapter.in.web.game;

import com.jaimemoreno.gameshelf.domain.game.Game;

public record GameResponse(
        Long id,
        String title,
        String description,
        String coverImageUrl,
        String releaseDate) {

    public static GameResponse from(Game game) {
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getCoverImageUrl(),
                game.getReleaseDate());
    }
}
