package com.jaimemoreno.gameshelf.adapter.out.external.igdb;

import com.jaimemoreno.gameshelf.adapter.out.external.dto.IgdbGameDto;
import com.jaimemoreno.gameshelf.adapter.out.external.dto.IgdbNamedEntityDto;
import com.jaimemoreno.gameshelf.application.game.GameSearchException;
import com.jaimemoreno.gameshelf.application.game.port.out.GameSearchPort;
import com.jaimemoreno.gameshelf.domain.game.GameSearchResult;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public final class IgdbGameSearchAdapter implements GameSearchPort {

    private final RestClient restClient;
    private final IgdbTokenProvider tokenProvider;
    private final String clientId;
    private final String baseUrl;

    public IgdbGameSearchAdapter(RestClient restClient, IgdbTokenProvider tokenProvider, String clientId, String baseUrl) {
        this.restClient = restClient;
        this.tokenProvider = tokenProvider;
        this.clientId = clientId;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<GameSearchResult> search(String query, int limit) {
        int clampedLimit = Math.clamp(limit, 1, 500);
        String escaped = query.replace("\\", "\\\\").replace("\"", "\\\"");
        String body = "search \"" + escaped + "\"; fields name, cover.*, first_release_date, platforms.*, genres.*, summary, rating, screenshots.*; limit " + clampedLimit + ";";
        String base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;

        IgdbGameDto[] result;
        try {
            result = restClient.post()
                    .uri(base + "/games")
                    .headers(h -> {
                        h.set("Client-ID", clientId);
                        h.setBearerAuth(tokenProvider.getAccessToken());
                        h.setAccept(List.of(MediaType.APPLICATION_JSON));
                    })
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(body)
                    .retrieve()
                    .body(IgdbGameDto[].class);
        } catch (RestClientException e) {
            throw new GameSearchException("Game search provider is unavailable", e);
        }

        if (result == null) {
            return List.of();
        }

        return Arrays.stream(result)
                .map(this::toDomain)
                .toList();
    }

    private GameSearchResult toDomain(IgdbGameDto dto) {
        Instant release = dto.firstReleaseDate() == null ? null : Instant.ofEpochSecond(dto.firstReleaseDate());
        String coverUrl = dto.cover() == null || dto.cover().url() == null ? null : absoluteUrl(dto.cover().url());
        List<String> platforms = dto.platforms() == null ? List.of() : dto.platforms().stream()
                .filter(p -> p != null && p.name() != null && !p.name().isBlank())
                .map(platform -> platform.name())
                .toList();
        List<String> genres = dto.genres() == null ? List.of() : dto.genres().stream()
                .filter(g -> g != null && g.name() != null && !g.name().isBlank())
                .map(genre -> genre.name())
                .toList();
        List<String> screenshots = dto.screenshots() == null ? List.of() : dto.screenshots().stream()
                .filter(s -> s != null && s.url() != null)
                .map(s -> absoluteUrl(s.url()))
                .toList();
        return new GameSearchResult(
                dto.id(),
                dto.name(),
                dto.summary(),
                coverUrl,
                release,
                dto.rating(),
                platforms,
                genres,
                screenshots);
    }

    private String absoluteUrl(String url) {
        return url.startsWith("//") ? "https:" + url : url;
    }
}
