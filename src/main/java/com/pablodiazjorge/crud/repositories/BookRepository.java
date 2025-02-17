package com.pablodiazjorge.crud.repositories;

import com.pablodiazjorge.crud.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { //using JpaRepository adds some basic methods like getAll(), getById(), update, create...
}
