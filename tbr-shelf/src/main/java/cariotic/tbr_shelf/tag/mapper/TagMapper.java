package cariotic.tbr_shelf.tag.mapper;

import cariotic.tbr_shelf.tag.dto.TagRequestDto;
import cariotic.tbr_shelf.tag.dto.TagResponseDto;
import cariotic.tbr_shelf.tag.model.Tag;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TagMapper {

    public TagResponseDto entityToDto(Tag tag){
        return TagResponseDto.builder()
                .name(tag.getName())
                .books(tag.getBooks()
                        .stream()
                        .map(b -> b.toString())
                        .collect(Collectors.toList()))
                .build();
    }

    public Tag dtoToEntity(TagRequestDto tagDto){
        return Tag.builder()
                .name(tagDto.name())
                .build();
    }
}
