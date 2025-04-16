package cariotic.tbr_shelf.book.mapper;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.enums.Status;
import cariotic.tbr_shelf.book.model.Book;
import cariotic.tbr_shelf.tag.model.Tag;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public Book dtoToEntity(BookRequestDto dto, Set<Tag> tags){
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .datePurchased(dto.datePurchased())
                .reasonBought(dto.reasonBought())
                .status(stringToStatus(dto.status()))
                .tags(tags)
                .build();
    }

    public BookResponseDto entityToDto(Book book){
        return BookResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .datePurchased(book.getDatePurchased())
                .reasonBought(book.getReasonBought())
                .status(book.getStatus().toString())
                .tags(book.getTags().stream()
                        .map(tag -> tag.getName())
                        .collect(Collectors.toSet()))
                .build();
    }

    public Status stringToStatus(String statusStr){
        return switch (statusStr) {
            case "UNREAD" -> Status.UNREAD;
            case "READING" -> Status.READING;
            case "FINISHED" -> Status.FINISHED;
            case "DNF" -> Status.DNF;
            default -> Status.UNREAD;
        };
    }
}
