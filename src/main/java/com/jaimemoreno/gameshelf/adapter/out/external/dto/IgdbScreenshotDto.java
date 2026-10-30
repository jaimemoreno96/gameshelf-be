package com.jaimemoreno.gameshelf.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IgdbScreenshotDto(Long id, String url) {
}
