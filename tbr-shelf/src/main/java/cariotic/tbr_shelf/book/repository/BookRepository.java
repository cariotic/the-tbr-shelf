package cariotic.tbr_shelf.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cariotic.tbr_shelf.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
