package com.jaimemoreno.gameshelf.config;

import com.jaimemoreno.gameshelf.adapter.out.external.igdb.IgdbGameSearchAdapter;
import com.jaimemoreno.gameshelf.adapter.out.external.igdb.IgdbTokenProvider;
import com.jaimemoreno.gameshelf.application.game.port.out.GameSearchPort;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class IgdbConfiguration {

    private SimpleClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));
        return factory;
    }

    @Bean
    RestClient igdbRestClient() {
        return RestClient.builder()
                .requestFactory(requestFactory())
                .build();
    }

    @Bean
    RestClient twitchTokenRestClient() {
        return RestClient.builder()
                .requestFactory(requestFactory())
                .build();
    }

    @Bean
    IgdbTokenProvider igdbTokenProvider(
            @Value("${igdb.client-id}") String clientId,
            @Value("${igdb.client-secret}") String clientSecret,
            @Value("${igdb.token-url}") String tokenUrl,
            @Qualifier("twitchTokenRestClient") RestClient restClient) {
        return new IgdbTokenProvider(clientId, clientSecret, tokenUrl, restClient);
    }

    @Bean
    GameSearchPort gameSearchPort(
            @Qualifier("igdbRestClient") RestClient restClient,
            IgdbTokenProvider tokenProvider,
            @Value("${igdb.client-id}") String clientId,
            @Value("${igdb.base-url}") String baseUrl) {
        return new IgdbGameSearchAdapter(restClient, tokenProvider, clientId, baseUrl);
    }
}
