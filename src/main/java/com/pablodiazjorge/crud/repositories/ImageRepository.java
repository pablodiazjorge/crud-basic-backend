package com.pablodiazjorge.crud.repositories;

import com.pablodiazjorge.crud.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
