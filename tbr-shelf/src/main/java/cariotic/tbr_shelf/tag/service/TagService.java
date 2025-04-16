package cariotic.tbr_shelf.tag.service;

import cariotic.tbr_shelf.tag.dto.TagRequestDto;
import cariotic.tbr_shelf.tag.dto.TagResponseDto;
import cariotic.tbr_shelf.tag.mapper.TagMapper;
import cariotic.tbr_shelf.tag.model.Tag;
import cariotic.tbr_shelf.tag.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper){
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagResponseDto> findAll(){
        return tagRepository.findAll().stream().map(tagMapper::entityToDto).collect(Collectors.toList());
    }

    public TagResponseDto findById(Long id) throws EntityNotFoundException {
        return tagRepository.findById(id).map(tagMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Tag with ID " + id + " not found"));
    }

    public TagResponseDto save(TagRequestDto tagDto){
        Tag tag = tagMapper.dtoToEntity(tagDto);
        return tagMapper.entityToDto(tagRepository.save(tag));
    }

    public TagResponseDto update(Long id, TagRequestDto tagDto) throws EntityNotFoundException{
        if (!tagRepository.existsById(id)){
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }
        return tagMapper.entityToDto(tagRepository.save(tagMapper.dtoToEntity(tagDto)));
    }

    public void delete(Long id) throws EntityNotFoundException{
        if (!tagRepository.existsById(id)){
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }
        tagRepository.deleteById(id);
    }
}
