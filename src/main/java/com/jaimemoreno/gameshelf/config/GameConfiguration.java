package com.jaimemoreno.gameshelf.config;

import com.jaimemoreno.gameshelf.application.game.port.out.GameRepository;
import com.jaimemoreno.gameshelf.application.game.port.out.GameSearchPort;
import com.jaimemoreno.gameshelf.application.game.service.GameSearchService;
import com.jaimemoreno.gameshelf.application.game.service.GameService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

    @Bean
    GameService gameService(GameRepository gameRepository) {
        return new GameService(gameRepository);
    }

    @Bean
    GameSearchService gameSearchService(GameSearchPort gameSearchPort) {
        return new GameSearchService(gameSearchPort);
    }
}
