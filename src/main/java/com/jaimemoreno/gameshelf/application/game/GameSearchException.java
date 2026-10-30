package com.jaimemoreno.gameshelf.application.game;

public class GameSearchException extends RuntimeException {

    public GameSearchException(String message) {
        super(message);
    }

    public GameSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
