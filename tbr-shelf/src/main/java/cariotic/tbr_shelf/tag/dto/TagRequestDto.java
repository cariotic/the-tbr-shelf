package cariotic.tbr_shelf.tag.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TagRequestDto(
        String name
) { }
