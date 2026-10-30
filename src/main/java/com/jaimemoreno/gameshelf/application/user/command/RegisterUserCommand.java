package com.jaimemoreno.gameshelf.application.user.command;

public record RegisterUserCommand(
        String username,
        String password) {
}
