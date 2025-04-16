package cariotic.tbr_shelf.tag.mapper;

import cariotic.tbr_shelf.tag.dto.TagRequestDto;
import cariotic.tbr_shelf.tag.dto.TagResponseDto;
import cariotic.tbr_shelf.tag.model.Tag;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TagMapper {

    public TagResponseDto entityToDto(Tag tag){
        return TagResponseDto.builder()
                .name(tag.getName())
                .books(Optional.ofNullable(tag.getBooks())
                        .stream()
                        .flatMap(Collection::stream)
                        .map(b -> b.getTitle())
                        .collect(Collectors.toList()))
                .build();
    }

    public Tag dtoToEntity(TagRequestDto tagDto){
        return Tag.builder()
                .name(tagDto.name())
                .build();
    }
}
