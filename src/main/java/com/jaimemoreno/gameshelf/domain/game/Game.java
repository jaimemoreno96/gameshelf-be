package com.jaimemoreno.gameshelf.domain.game;

public final class Game {

    private final Long id;
    private final String title;
    private final String description;
    private final String coverImageUrl;
    private final String releaseDate;

    public Game(
            Long id,
            String title,
            String description,
            String coverImageUrl,
            String releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
