package cariotic.tbr_shelf.tag.service;

import cariotic.tbr_shelf.tag.model.Tag;
import cariotic.tbr_shelf.tag.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    public Tag findById(Long id) throws EntityNotFoundException {
        return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag with ID " + id + " not found"));
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag update(Long id, Tag tag) throws EntityNotFoundException{
        if (!tagRepository.existsById(id)){
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }
        return tagRepository.save(tag);
    }

    public void delete(Long id) throws EntityNotFoundException{
        if (!tagRepository.existsById(id)){
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }
        tagRepository.deleteById(id);
    }
}
