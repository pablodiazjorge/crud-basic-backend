package com.pablodiazjorge.crud.services;

import com.pablodiazjorge.crud.entities.Book;
import com.pablodiazjorge.crud.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{


    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book){
        return bookRepository.save(book);
    }
    @Override
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    @Override
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}