package com.jaimemoreno.gameshelf.adapter.in.web.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank @Size(min = 3, max = 100) String username,
        @NotBlank @Size(min = 8, max = 255) String password) {
}
