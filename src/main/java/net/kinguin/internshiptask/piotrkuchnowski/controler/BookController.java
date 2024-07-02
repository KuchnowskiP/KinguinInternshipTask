package net.kinguin.internshiptask.piotrkuchnowski.controler;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @GetMapping("/title/{title}")
    public List<Book> getBookByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book book) {
        return bookRepository.save(
                new Book(
                        id,
                        book.title(),
                        book.author(),
                        book.publisher(),
                        book.genre(),
                        book.isbn())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id);
    }

}
