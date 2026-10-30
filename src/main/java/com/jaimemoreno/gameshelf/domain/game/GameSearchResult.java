package com.jaimemoreno.gameshelf.domain.game;

import java.time.Instant;
import java.util.List;

public final class GameSearchResult {

    private final Long externalId;
    private final String name;
    private final String summary;
    private final String coverUrl;
    private final Instant firstReleaseDate;
    private final Double rating;
    private final List<String> platforms;
    private final List<String> genres;
    private final List<String> screenshotUrls;

    public GameSearchResult(
            Long externalId,
            String name,
            String summary,
            String coverUrl,
            Instant firstReleaseDate,
            Double rating,
            List<String> platforms,
            List<String> genres,
            List<String> screenshotUrls) {
        this.externalId = externalId;
        this.name = name;
        this.summary = summary;
        this.coverUrl = coverUrl;
        this.firstReleaseDate = firstReleaseDate;
        this.rating = rating;
        this.platforms = List.copyOf(platforms == null ? List.of() : platforms);
        this.genres = List.copyOf(genres == null ? List.of() : genres);
        this.screenshotUrls = List.copyOf(screenshotUrls == null ? List.of() : screenshotUrls);
    }

    public Long getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Instant getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getScreenshotUrls() {
        return screenshotUrls;
    }
}
