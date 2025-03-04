package com.pablodiazjorge.crud.services;

import com.pablodiazjorge.crud.entities.Book;
import com.pablodiazjorge.crud.dto.BookWithImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book, MultipartFile file) throws IOException;
    Book updateBook(Book book);
    Page<BookWithImageDTO> getBooks(org.springframework.data.domain.Pageable pageable, String query);
    Optional<Book> getBookById(Long id);
    void deleteBook(Book book) throws IOException;
    Book updateBookImage(MultipartFile file, Book book) throws IOException;
}
