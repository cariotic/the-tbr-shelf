package cariotic.tbr_shelf.book.service;

import cariotic.tbr_shelf.book.model.Book;
import cariotic.tbr_shelf.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) throws EntityNotFoundException{
        if (!bookRepository.existsById(id)){
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        return bookRepository.save(book);
    }

    public void delete(Long id) throws EntityNotFoundException{
        if (!bookRepository.existsById(id)){
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
