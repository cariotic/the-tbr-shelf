package cariotic.tbr_shelf.book.service;

import cariotic.tbr_shelf.tag.dto.TagRequestDto;
import cariotic.tbr_shelf.tag.dto.TagResponseDto;
import cariotic.tbr_shelf.tag.mapper.TagMapper;
import cariotic.tbr_shelf.tag.model.Tag;
import cariotic.tbr_shelf.tag.repository.TagRepository;
import cariotic.tbr_shelf.tag.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    TagService tagService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsMappedList(){
        Tag tag = new Tag(1L, "Fantasy", null);
        TagResponseDto dto = new TagResponseDto("Fantasy", null);

        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagMapper.entityToDto(tag)).thenReturn(dto);

        List<TagResponseDto> result = tagService.findAll();

        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .containsExactly(dto);

        verify(tagRepository).findAll();
        verify(tagMapper).entityToDto(tag);
    }

    @Test
    void findById_Found_ReturnsDto(){
        Tag tag = new Tag(1L, "Fiction", null);
        TagResponseDto dto = new TagResponseDto("Fiction", null);

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagMapper.entityToDto(tag)).thenReturn(dto);

        TagResponseDto result = tagService.findById(1L);

        assertThat(result).isSameAs(dto);

        verify(tagRepository).findById(1L);
        verify(tagMapper).entityToDto(tag);
    }

    @Test
    void save_ReturnsDto(){
        Tag tag = new Tag(1L, "Fiction", null);
        TagRequestDto requestDto = new TagRequestDto("Fiction");
        TagResponseDto responseDto = new TagResponseDto("Fiction", null);

        when(tagMapper.dtoToEntity(requestDto)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.entityToDto(tag)).thenReturn(responseDto);

        TagResponseDto result = tagService.save(requestDto);

        assertThat(result).isEqualTo(responseDto);

        verify(tagMapper).dtoToEntity(requestDto);
        verify(tagRepository).save(tag);
        verify(tagMapper).entityToDto(tag);
    }

    @Test
    void update_TagNotExists_ThrowsException(){
        when(tagRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> tagService.update(1L, mock(TagRequestDto.class)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag with ID 1 not found");
    }

    @Test
    void delete_TagExists_DeletesSuccessfully(){
        when(tagRepository.existsById(1L)).thenReturn(true);

        tagService.delete(1L);

        verify(tagRepository).deleteById(1L);
    }

    @Test
    void delete_TagNotExists_ThrowsException(){
        when(tagRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> tagService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag with ID 1 not found");
    }
}
