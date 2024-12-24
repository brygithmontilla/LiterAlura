package com.example.aluracursos.LiterAlura.repository;

import com.example.aluracursos.LiterAlura.model.Autor;
import com.example.aluracursos.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {


    List<Autor> findByNombreContainsIgnoreCase(String nombre);
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= ?1 AND a.fechaDeFallecimiento >= ?1")
    List<Autor> buscarAutoresVivosEnDeterminadoAño(Integer anio);

}
