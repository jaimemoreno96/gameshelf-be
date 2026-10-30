package com.jaimemoreno.gameshelf.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IgdbCoverDto(Long id, String url) {
}
