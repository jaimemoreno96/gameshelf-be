package com.jaimemoreno.gameshelf.adapter.out.external.igdb;

import com.jaimemoreno.gameshelf.adapter.out.external.dto.TwitchTokenResponse;
import com.jaimemoreno.gameshelf.application.game.GameSearchException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

public final class IgdbTokenProvider {

    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final RestClient restClient;
    private String cachedAccessToken;
    private Instant expiresAt;

    public IgdbTokenProvider(String clientId, String clientSecret, String tokenUrl, RestClient restClient) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.restClient = restClient;
    }

    public synchronized String getAccessToken() {
        if (cachedAccessToken != null && Instant.now().isBefore(expiresAt.minusSeconds(60))) {
            return cachedAccessToken;
        }
        var uri = UriComponentsBuilder.fromUriString(tokenUrl)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "client_credentials")
                .build()
                .toUri();
        TwitchTokenResponse token;
        try {
            token = restClient.post()
                    .uri(uri)
                    .retrieve()
                    .body(TwitchTokenResponse.class);
        } catch (RestClientException e) {
            throw new GameSearchException("Failed to obtain IGDB access token", e);
        }
        if (token == null || token.accessToken() == null || token.accessToken().isBlank() || token.expiresIn() <= 0) {
            throw new GameSearchException("Failed to obtain IGDB access token");
        }
        cachedAccessToken = token.accessToken();
        expiresAt = Instant.now().plusSeconds(token.expiresIn());
        return cachedAccessToken;
    }
}
