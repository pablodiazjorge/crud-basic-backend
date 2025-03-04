package com.pablodiazjorge.crud.repositories;

import com.pablodiazjorge.crud.entities.Book;
import com.pablodiazjorge.crud.dto.BookWithImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.*;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> { //using JpaRepository adds some basic methods like getAll(), getById(), update, create...

    @Query(value = "SELECT b.id, b.title, b.author, b.pages, b.price, " +
            "i.id as imageId, i.name as imageName, i.image_url as imageUrl, i.image_id as imageImageId " +
            "FROM book b " +
            "LEFT JOIN image i ON b.image_id = i.id " +
            "WHERE (:query IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:query AS VARCHAR), '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', CAST(:query AS VARCHAR), '%')))",
            countQuery = "SELECT count(*) FROM book b WHERE (:query IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:query AS VARCHAR), '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', CAST(:query AS VARCHAR), '%')))",
            nativeQuery = true)
    Page<BookWithImageDTO> findByTitleOrAuthorContainingIgnoreCase(@Param("query") String query, Pageable pageable);
}