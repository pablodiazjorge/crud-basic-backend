package com.pablodiazjorge.crud.services;

import com.pablodiazjorge.crud.entities.Book;
import com.pablodiazjorge.crud.entities.Image;
import com.pablodiazjorge.crud.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{


    private final BookRepository bookRepository;
    private final ImageService imageService;

    public BookServiceImpl(BookRepository bookRepository, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.imageService = imageService;
    }

    /**
     * Saves a book and optionally associates an image with it.
     *
     * @param book The book object to save.
     * @param file The multipart file representing the image to upload (optional).
     * @return Book - The saved book object.
     * @throws IOException If an I/O error occurs while uploading the image.
     */
    @Override
    public Book saveBook(Book book, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()){
            Image image = imageService.uploadImage(file);
            book.setImage(image);
        }
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

    /**
     * Deletes a book and its associated image if it exists.
     *
     * @param book The book object to delete.
     * @throws IOException If an I/O error occurs while deleting the image.
     */
    @Override
    public void deleteBook(Book book) throws IOException {
        if (book.getImage() != null) {
            imageService.deleteImage(book.getImage());
        }
        bookRepository.deleteById(book.getId());
    }

    /**
     * Updates the image of a book.
     *
     * @param file The multipart file representing the new image.
     * @param book The book object to update.
     * @return Book - The updated book object with the new image.
     * @throws IOException If an I/O error occurs while uploading or deleting the image.
     */
    @Override
    public Book updateBookImage(MultipartFile file, Book book) throws IOException {
        if (book.getImage() != null) {
            imageService.deleteImage(book.getImage());
        }
        Image newImage = imageService.uploadImage(file);
        book.setImage(newImage);
        return bookRepository.save(book);
    }
}