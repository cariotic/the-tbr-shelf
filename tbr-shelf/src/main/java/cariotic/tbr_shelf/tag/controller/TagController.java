package cariotic.tbr_shelf.tag.controller;

import cariotic.tbr_shelf.tag.service.TagService;
import cariotic.tbr_shelf.tag.model.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<Tag>> getAllTags(){
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id){
        try{
            Tag tag = tagService.findById(id);
            return ResponseEntity.ok(tag);
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        Tag createdTag = tagService.save(tag);
        return ResponseEntity
                .created(URI.create("/tags/" + createdTag.getId()))
                .body(createdTag);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag){
        try {
            tagService.update(id, tag);
            return ResponseEntity.ok(tag);
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id){
        try{
            tagService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
