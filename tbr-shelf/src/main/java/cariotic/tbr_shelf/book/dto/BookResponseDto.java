package cariotic.tbr_shelf.book.dto;

import cariotic.tbr_shelf.book.enums.Status;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record BookResponseDto (
        String title,
        String author,
        LocalDate datePurchased,
        String reasonBought,
        Set<String> tags,
        String status
) { }
