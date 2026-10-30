package com.jaimemoreno.gameshelf.adapter.in.web.game;

import com.jaimemoreno.gameshelf.domain.game.GameSearchResult;

import java.time.Instant;
import java.util.List;

public record GameSearchResponse(
        Long externalId,
        String name,
        String summary,
        String coverUrl,
        Instant firstReleaseDate,
        Double rating,
        List<String> platforms,
        List<String> genres,
        List<String> screenshotUrls) {

    public static GameSearchResponse from(GameSearchResult result) {
        return new GameSearchResponse(
                result.getExternalId(),
                result.getName(),
                result.getSummary(),
                result.getCoverUrl(),
                result.getFirstReleaseDate(),
                result.getRating(),
                result.getPlatforms(),
                result.getGenres(),
                result.getScreenshotUrls());
    }
}
