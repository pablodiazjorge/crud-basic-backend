package com.pablodiazjorge.crud.controllers;

import com.pablodiazjorge.crud.entities.Book;
import com.pablodiazjorge.crud.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/book")
@CrossOrigin("http://localhost:4200/")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookServiceImpl bookServiceImpl;

    /**
     * Saves a new book with an associated file.
     *
     * @param book   The book object containing details to be saved.
     * @param file   The multipart file associated with the book.
     * @return ResponseEntity<Book> - The saved book object with HTTP status OK on success,
     *                                or BAD_REQUEST if an exception occurs.
     */
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestPart("book") Book book, @RequestPart("file") MultipartFile file) {
        try {
            Book savedBook = bookServiceImpl.saveBook(book, file);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at PostMapping-saveBook() method", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Updates the image of a book by its ID.
     *
     * @param id     The ID of the book to update.
     * @param file   The multipart file representing the new image.
     * @return ResponseEntity<Book> - The updated book object with HTTP status OK on success,
     *                                or NOT_FOUND if the book does not exist.
     * @throws IOException If an I/O error occurs while processing the file.
     */
    @PutMapping("/{id}/image")
    public ResponseEntity<Book> updateBookImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if (book.isPresent()) {
            Book updatedBook = bookServiceImpl.updateBookImage(file, book.get());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing book.
     *
     * @param book The book object with updated details.
     * @return ResponseEntity<Book> - The updated book object with HTTP status OK on success,
     *                                or BAD_REQUEST if an exception occurs.
     */
    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        try {
            Book savedBook = bookServiceImpl.updateBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a paginated list of all books.
     *
     * @param page The page number (default is 0).
     * @param size The number of items per page (default is 10).
     * @return ResponseEntity<Page<Book>> - A paginated list of books with HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookServiceImpl.getBooks(pageable);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return ResponseEntity<Book> - The book object with HTTP status OK if found,
     *                                or NOT_FOUND if the book does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @return ResponseEntity<Void> - HTTP status OK if the book is deleted successfully,
     *                                or NOT_FOUND if the book does not exist.
     * @throws IOException If an I/O error occurs during deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws IOException {
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if (book.isPresent()){
            bookServiceImpl.deleteBook(book.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}