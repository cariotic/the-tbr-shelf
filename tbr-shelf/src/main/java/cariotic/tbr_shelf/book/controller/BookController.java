package cariotic.tbr_shelf.book.controller;

import cariotic.tbr_shelf.book.model.Book;
import cariotic.tbr_shelf.book.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        try{
            Book book = bookService.findById(id);
            return ResponseEntity.ok(book);
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book createdBook = bookService.save(book);
        return ResponseEntity
                .created(URI.create("/books/" + createdBook.getId()))
                .body(createdBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        try {
            bookService.update(id, book);
            return ResponseEntity.ok(book);
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
