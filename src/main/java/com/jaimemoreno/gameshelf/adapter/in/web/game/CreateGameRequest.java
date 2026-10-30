package com.jaimemoreno.gameshelf.adapter.in.web.game;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGameRequest(
        @NotBlank @Size(max = 255) String title,
        @Size(max = 10000) String description,
        @Size(max = 1024) String coverImageUrl,
        @NotBlank @Size(max = 32) String releaseDate) {
}
