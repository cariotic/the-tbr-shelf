package cariotic.tbr_shelf.book.service;

import cariotic.tbr_shelf.book.dto.BookRequestDto;
import cariotic.tbr_shelf.book.dto.BookResponseDto;
import cariotic.tbr_shelf.book.enums.Status;
import cariotic.tbr_shelf.book.exceptions.BookNotFoundException;
import cariotic.tbr_shelf.book.mapper.BookMapper;
import cariotic.tbr_shelf.book.model.Book;
import cariotic.tbr_shelf.book.repository.BookRepository;
import cariotic.tbr_shelf.tag.model.Tag;
import cariotic.tbr_shelf.tag.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private TagService tagService;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsMappedList() {
        Book book = new Book(1L, "The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", null, Status.UNREAD);
        BookResponseDto dto = new BookResponseDto("The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", null, Status.UNREAD.toString());
        List<Book> books = List.of(book);
        Pageable pageable = PageRequest.of(0, 1, Sort.by("title"));
        Page<Book> page = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.entityToDto(book)).thenReturn(dto);

        Page<BookResponseDto> result = bookService.findAll(pageable);

        assertThat(result.getNumber())
                .isEqualTo(0);
        assertThat(result.getTotalElements())
                .isEqualTo(1);
        assertThat(result.getContent())
                .isNotNull()
                .containsExactly(dto);

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).entityToDto(book);
    }

    @Test
    void findById_Found_ReturnsDto() {
        Book book = new Book(1L, "The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", null, Status.UNREAD);
        BookResponseDto dto = new BookResponseDto("The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", null, Status.UNREAD.toString());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(dto);

        BookResponseDto result = bookService.findById(1L);

        assertThat(result).isSameAs(dto);

        verify(bookRepository).findById(1L);
        verify(bookMapper).entityToDto(book);
    }

    @Test
    void save_ReturnsDto() {
        BookRequestDto requestDto = mock(BookRequestDto.class);
        Set<Tag> tags = Set.of(new Tag(1L, "Fiction", null));
        Book book = new Book(1L, "The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", tags, Status.UNREAD);
        BookResponseDto responseDto = new BookResponseDto("The Girl with the Dragon Tattoo", "Stieg Larsson", LocalDate.parse("2005-08-01"), "", Set.of("Fiction"), Status.UNREAD.toString());

        when(requestDto.tags()).thenReturn(Set.of("Fiction"));
        when(tagService.getTagsFromNames(any())).thenReturn(tags);
        when(bookMapper.dtoToEntity(requestDto, tags)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.entityToDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.save(requestDto);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void update_BookNotExists_ThrowsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.update(1L, mock(BookRequestDto.class)))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book with ID 1 not found");
    }

    @Test
    void delete_BookExists_DeletesSuccessfully() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.delete(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void delete_BookNotExists_ThrowsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.delete(1L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book with ID 1 not found");
    }
}