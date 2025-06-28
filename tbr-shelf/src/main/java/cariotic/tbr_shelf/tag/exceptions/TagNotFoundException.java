package cariotic.tbr_shelf.tag.exceptions;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id){
        super("Tag with ID " + id + " not found");
    }
}
