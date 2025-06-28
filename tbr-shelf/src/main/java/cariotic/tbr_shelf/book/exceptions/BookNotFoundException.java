package cariotic.tbr_shelf.book.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id){
        super("Book with ID " + id + " not found");
    }
}
