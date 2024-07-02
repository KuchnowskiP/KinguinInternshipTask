package net.kinguin.internshiptask.piotrkuchnowski.repository;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    Book findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisher(String publisher);
    List<Book> findByGenre(String genre);
    List<Book> findByTitleContainingIgnoreCase(String title);


}
