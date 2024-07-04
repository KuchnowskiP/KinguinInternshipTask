package net.kinguin.internshiptask.piotrkuchnowski.service;

import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.BookDTO;
import net.kinguin.internshiptask.piotrkuchnowski.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Book with id " + id + " does not exist"));
    }

    public Book createBook(BookDTO book) {
        if (bookRepository.findByIsbn(book.isbn()) != null) {
            throw new IllegalArgumentException("Book with isbn " + book.isbn() + " already exists");
        }
        return bookRepository.save(
                new Book(
                        null,
                        book.title(),
                        book.author(),
                        book.publisher(),
                        book.genre(),
                        book.isbn(),
                        book.totalCopies(),
                        book.availableCopies()));
    }

    public void deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Book with id " + id + " does not exist");
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(String id, BookDTO book) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Book with id " + id + " does not exist");
        }

        return bookRepository.save(
                new Book(
                        id,
                        book.title(),
                        book.author(),
                        book.publisher(),
                        book.genre(),
                        book.isbn(),
                        book.totalCopies(),
                        book.availableCopies()));
    }

    public List<Book> searchBooks(
            String title, String author, String publisher, String genre, String isbn) {

        if (title.isEmpty()
                && author.isEmpty()
                && publisher.isEmpty()
                && genre.isEmpty()
                && isbn.isEmpty()) {
            return bookRepository.findAll();
        }

        if (!isbn.isEmpty()) {
            Book book = bookRepository.findByIsbn(isbn);
            if (book == null) {
                return new ArrayList<>();
            }
            return List.of(book);
        }

        List<Book> books = new ArrayList<>();
        if (!title.isEmpty()) {
            books.addAll(bookRepository.findByTitleContainingIgnoreCase(title));
        }
        if (!author.isEmpty()) {
            books.addAll(bookRepository.findByAuthorContainingIgnoreCase(author));
        }
        if (!publisher.isEmpty()) {
            books.addAll(bookRepository.findByPublisherContainingIgnoreCase(publisher));
        }
        if (!genre.isEmpty()) {
            books.addAll(bookRepository.findByGenreContainingIgnoreCase(genre));
        }
        return books;
    }

    public boolean existsById(String id) {
        return bookRepository.existsById(id);
    }

    public void decrementAvailableCopies(String bookId) {
        Book book = getBookById(bookId);
        if (book.availableCopies() == 0) {
            throw new IllegalArgumentException(
                    "All copies of book with id " + bookId + " are loaned");
        }
        bookRepository.save(
                new Book(
                        book.id(),
                        book.title(),
                        book.author(),
                        book.publisher(),
                        book.genre(),
                        book.isbn(),
                        book.totalCopies(),
                        book.availableCopies() - 1));
    }

    public void incrementAvailableCopies(String bookId) {
        Book book = getBookById(bookId);
        if (Objects.equals(book.availableCopies(), book.totalCopies())) {
            throw new IllegalArgumentException(
                    "All copies of book with id " + bookId + " are available");
        }
        bookRepository.save(
                new Book(
                        book.id(),
                        book.title(),
                        book.author(),
                        book.publisher(),
                        book.genre(),
                        book.isbn(),
                        book.totalCopies(),
                        book.availableCopies() + 1));
    }
}
