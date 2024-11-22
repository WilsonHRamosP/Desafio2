package com.desafio2.repository;

import com.desafio2.model.Author;
import com.desafio2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// @Repository indica que esta interfaz es un repositorio y le dice a Spring que la implemente automáticamente
// para interactuar con la base de datos. Este repositorio maneja la entidad Book.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Método personalizado usando @Query para buscar un libro por su título sin importar mayúsculas o minúsculas
    // La consulta JPQL selecciona un libro de la entidad Book donde el título (title) coincide ignorando el caso (mayúsculas/minúsculas).
    // :title es un parámetro que se pasa al método.
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)")
    // findByTitleIgnoreCase devuelve un Optional<Book> para manejar el caso de que no se encuentre el libro.
    Optional<Book> findByTitleIgnoreCase(@Param("title") String title);

    // Método generado automáticamente por Spring Data JPA para buscar libros por idioma.
    // findByLanguage generará una consulta para buscar todos los libros cuyo campo "language" coincida con el parámetro.
    // Devuelve una lista de libros en el idioma especificado.
    List<Book> findByLanguage(String language);

    // Método generado automáticamente para encontrar libros que contienen al autor especificado.
    // findByAuthorsContaining crea una consulta que busca libros que tengan un autor específico.
    // Devuelve una lista de libros en los que el autor es uno de los autores del libro.
    List<Book> findByAuthorsContaining(Author author);

    // Método personalizado usando @Query para encontrar idiomas distintos (sin valores nulos).
    // La consulta JPQL selecciona los valores distintos del campo "language" de la entidad Book.
    // Esto elimina los valores nulos y devuelve solo los idiomas distintos en los que están escritos los libros.
    @Query("SELECT DISTINCT b.language FROM Book b WHERE b.language IS NOT NULL")
    // Devuelve una lista de idiomas distintos disponibles en la base de datos.
    List<String> findDistinctLanguages();
}

