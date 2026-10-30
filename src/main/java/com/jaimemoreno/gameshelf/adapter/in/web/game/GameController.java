package com.jaimemoreno.gameshelf.adapter.in.web.game;

import com.jaimemoreno.gameshelf.application.game.command.CreateGameCommand;
import com.jaimemoreno.gameshelf.application.game.command.SearchGamesCommand;
import com.jaimemoreno.gameshelf.application.game.port.in.CreateGameUseCase;
import com.jaimemoreno.gameshelf.application.game.port.in.FindGamesUseCase;
import com.jaimemoreno.gameshelf.application.game.port.in.SearchGamesUseCase;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final FindGamesUseCase findGamesUseCase;
    private final SearchGamesUseCase searchGamesUseCase;

    public GameController(
            CreateGameUseCase createGameUseCase,
            FindGamesUseCase findGamesUseCase,
            SearchGamesUseCase searchGamesUseCase) {
        this.createGameUseCase = createGameUseCase;
        this.findGamesUseCase = findGamesUseCase;
        this.searchGamesUseCase = searchGamesUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse create(@Valid @RequestBody CreateGameRequest request) {
        CreateGameCommand command = new CreateGameCommand(
                request.title(),
                request.description(),
                request.coverImageUrl(),
                request.releaseDate());
        return GameResponse.from(createGameUseCase.create(command));
    }

    @GetMapping
    public List<GameResponse> findAll() {
        return findGamesUseCase.findAll().stream()
                .map(GameResponse::from)
                .toList();
    }

    @GetMapping("/search")
    public List<GameSearchResponse> search(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "limit", defaultValue = "20") int limit) {
        return searchGamesUseCase.search(new SearchGamesCommand(query, limit)).stream()
                .map(GameSearchResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> findById(@PathVariable Long id) {
        return findGamesUseCase.findById(id)
                .map(GameResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
