package net.kinguin.internshiptask.piotrkuchnowski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.kinguin.internshiptask.piotrkuchnowski.model.Book;
import net.kinguin.internshiptask.piotrkuchnowski.model.dto.BookDTO;
import net.kinguin.internshiptask.piotrkuchnowski.response.ApiErrorResponse;
import net.kinguin.internshiptask.piotrkuchnowski.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Get all books",
            description = "Gets all books",
            responses = {
                @ApiResponse(
                        description = "List of books",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Book.class))),
                @ApiResponse(description = "No books found", responseCode = "204", content = @Content)
            })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return !books.isEmpty() ? ResponseEntity.ok(books) : ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get book by id",
            description = "Gets book by id",
            responses = {
                @ApiResponse(
                        description = "Book object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Book.class))),
                @ApiResponse(description = "Book with given id does not exist", responseCode = "400", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)
                ))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(
            summary = "Create book",
            description = "Creates book",
            responses = {
                @ApiResponse(
                        description = "Book object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Book.class))),
                @ApiResponse(description = "Book with given isbn already exists", responseCode = "400", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)
                ))
            })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
        Book bookResponse = bookService.createBook(book);
        return ResponseEntity.ok(bookResponse);
    }

    @Operation(
            summary = "Update book",
            description = "Updates book",
            responses = {
                @ApiResponse(
                        description = "Book object",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Book.class))),
                @ApiResponse(description = "Book with given id does not exist", responseCode = "400", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)
                ))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody BookDTO book) {
        Book bookResponse = bookService.updateBook(id, book);
        return ResponseEntity.ok(bookResponse);
    }

    @Operation(
            summary = "Delete book",
            description = "Deletes book",
            responses = {
                @ApiResponse(description = "Book deleted", responseCode = "200"),
                @ApiResponse(description = "Book with given id does not exist", responseCode = "400", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)
                ))
            })
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @Operation(
            summary = "Search books",
            description = "Searches books by title, author, publisher, genre or isbn",
            responses = {
                @ApiResponse(
                        description = "List of books",
                        responseCode = "200",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Book.class))),
                @ApiResponse(description = "No books found, based on provided parameters", responseCode = "204", content = @Content)
            })
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String publisher,
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false, defaultValue = "") String isbn) {
        List<Book> books = bookService.searchBooks(title, author, publisher, genre, isbn);
        return !books.isEmpty() ? ResponseEntity.ok(books) : ResponseEntity.noContent().build();
    }
}
