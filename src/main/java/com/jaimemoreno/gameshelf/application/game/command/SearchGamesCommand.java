package com.jaimemoreno.gameshelf.application.game.command;

public record SearchGamesCommand(String query, int limit) {
}
