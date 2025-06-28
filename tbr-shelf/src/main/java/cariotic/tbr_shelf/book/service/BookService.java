package cariotic.tbr_shelf.book.service;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.exceptions.BookNotFoundException;
import cariotic.tbr_shelf.book.mapper.BookMapper;
import cariotic.tbr_shelf.book.repository.BookRepository;
import cariotic.tbr_shelf.tag.model.Tag;
import cariotic.tbr_shelf.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final TagService tagService;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, TagService tagService){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.tagService = tagService;
    }

    public List<BookResponseDto> findAll(){
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto)
                .toList();
    }

    public BookResponseDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::entityToDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookResponseDto save(BookRequestDto bookDto){
        Set<Tag> tags = tagService.getTagsFromNames(bookDto.tags());
        return bookMapper.entityToDto(bookRepository.save(bookMapper.dtoToEntity(bookDto, tags)));
    }

    public BookResponseDto update(Long id, BookRequestDto bookDto) {
        if (!bookRepository.existsById(id)){
            throw new BookNotFoundException(id);
        }
        Set<Tag> tags = tagService.getTagsFromNames(bookDto.tags());
        return bookMapper.entityToDto(bookRepository.save(bookMapper.dtoToEntity(bookDto, tags)));
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)){
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}
