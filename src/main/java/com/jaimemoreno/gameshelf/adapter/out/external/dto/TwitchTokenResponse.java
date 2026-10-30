package com.jaimemoreno.gameshelf.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TwitchTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn) {
}
