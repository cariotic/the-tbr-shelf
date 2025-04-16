package cariotic.tbr_shelf.book.mapper;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.enums.Status;
import cariotic.tbr_shelf.book.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book dtoToEntity(BookRequestDto dto){
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .datePurchased(dto.datePurchased())
                .reasonBought(dto.reasonBought())
                .status(stringToStatus(dto.status()))
                .build();
    }

    public BookResponseDto entityToDto(Book book){
        return BookResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .datePurchased(book.getDatePurchased())
                .reasonBought(book.getReasonBought())
                .status(book.getStatus().toString())
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
