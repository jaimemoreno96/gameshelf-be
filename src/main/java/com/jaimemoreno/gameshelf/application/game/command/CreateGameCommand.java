package com.jaimemoreno.gameshelf.application.game.command;

public record CreateGameCommand(
        String title,
        String description,
        String coverImageUrl,
        String releaseDate) {
}
