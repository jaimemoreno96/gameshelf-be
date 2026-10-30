package com.jaimemoreno.gameshelf.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IgdbNamedEntityDto(Long id, String name) {
}
