package com.desafio2.repository;

import com.desafio2.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

// Esta es una interfaz que actúa como un repositorio para manejar la persistencia de entidades Author.
// Extiende JpaRepository para proporcionar operaciones CRUD y consultas avanzadas.
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Método personalizado para buscar un autor por su nombre.
    // Utiliza la convención de Spring Data JPA para crear la consulta automáticamente.
    Optional<Author> findByName(String name);

    // Método para encontrar autores nacidos en un año igual o posterior y que aún estén vivos (deathYear es null).
    // También utiliza la convención de nombres de Spring Data JPA para generar la consulta.
    List<Author> findByBirthYearGreaterThanEqualAndDeathYearIsNull(int year);

    // Consulta personalizada escrita en JPQL (Java Persistence Query Language).
    // Devuelve autores que estuvieron vivos en un año específico:
    // 1. `birthYear <= :year` asegura que nacieron antes o durante ese año.
    // 2. `(deathYear IS NULL OR deathYear >= :year)` verifica que no hayan muerto antes de ese año.
    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findLivingAuthorsInYear(@Param("year") int year);
}
