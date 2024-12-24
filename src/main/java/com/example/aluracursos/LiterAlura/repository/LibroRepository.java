package com.example.aluracursos.LiterAlura.repository;

import com.example.aluracursos.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {


    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autor ")
    List<Libro> findAllWithAuthors();


    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autor WHERE ?1 MEMBER OF l.idioma")
    List<Libro> findByIdiomasAllWithAuthors( String idioma);


}
