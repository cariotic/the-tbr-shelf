package cariotic.tbr_shelf.book.controller;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id){
        try{
            BookResponseDto book = bookService.findById(id);
            return ResponseEntity.ok(book);
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto bookDto){
        BookResponseDto createdBook = bookService.save(bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookDto){
        try {
            return ResponseEntity.ok(bookService.update(id, bookDto));
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
