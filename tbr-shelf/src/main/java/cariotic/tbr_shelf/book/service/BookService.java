package cariotic.tbr_shelf.book.service;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.mapper.BookMapper;
import cariotic.tbr_shelf.book.model.Book;
import cariotic.tbr_shelf.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseDto> findAll(){
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::BookToDto)
                .toList();
    }

    public BookResponseDto findById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
                .map(bookMapper::BookToDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));
    }

    public BookResponseDto save(BookRequestDto bookDto){
        return bookMapper.BookToDto(bookRepository.save(bookMapper.DtoToEntity(bookDto)));
    }

    public BookResponseDto update(Long id, BookRequestDto bookDto) throws EntityNotFoundException{
        if (!bookRepository.existsById(id)){
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        return bookMapper.BookToDto(bookRepository.save(bookMapper.DtoToEntity(bookDto)));
    }

    public void delete(Long id) throws EntityNotFoundException{
        if (!bookRepository.existsById(id)){
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
