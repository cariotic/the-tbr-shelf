package cariotic.tbr_shelf.book.dto;

import cariotic.tbr_shelf.book.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

public record BookRequestDto(
        String title,
        String author,
        LocalDate datePurchased,
        String reasonBought,
        Set<String> tags,
        String status
) {}

