package com.jaimemoreno.gameshelf.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IgdbGameDto(
        Long id,
        String name,
        String summary,
        Double rating,
        @JsonProperty("first_release_date") Long firstReleaseDate,
        IgdbCoverDto cover,
        List<IgdbNamedEntityDto> platforms,
        List<IgdbNamedEntityDto> genres,
        List<IgdbScreenshotDto> screenshots) {
}
