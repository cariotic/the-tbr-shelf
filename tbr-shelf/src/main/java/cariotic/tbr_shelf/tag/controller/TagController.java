package cariotic.tbr_shelf.tag.controller;

import cariotic.tbr_shelf.tag.dto.TagRequestDto;
import cariotic.tbr_shelf.tag.dto.TagResponseDto;
import cariotic.tbr_shelf.tag.exceptions.TagNotFoundException;
import cariotic.tbr_shelf.tag.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService){
        this. tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<Page<TagResponseDto>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return ResponseEntity.ok(tagService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTag(@PathVariable Long id){
        try{
            return ResponseEntity.ok(tagService.findById(id));
        } catch(TagNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.save(tagDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable Long id, @RequestBody TagRequestDto tagDto){
        try {
            return ResponseEntity.ok(tagService.update(id, tagDto));
        } catch(TagNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id){
        try{
            tagService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(TagNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
