package com.desafio2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Entity: Define la clase como una entidad JPA, lo que significa que esta clase está mapeada a una tabla en la base de datos.
@Entity
// @Table: Especifica el nombre de la tabla en la base de datos para esta entidad.
// uniqueConstraints: Garantiza que el campo 'title' sea único en la base de datos, es decir, no puede haber dos libros con el mismo título.
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Book {

    // @Id: Indica que este campo es la clave primaria de la entidad 'Book'.
    // @GeneratedValue(strategy = GenerationType.IDENTITY): Indica que el valor de 'id' será generado automáticamente por la base de datos (autoincremental).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column: Mapea el campo 'title' a una columna en la base de datos.
    // nullable = false: El título no puede ser nulo.
    // unique = true: El título debe ser único (no pueden existir dos libros con el mismo título en la base de datos).
    @Column(nullable = false, unique = true) // Garantizar unicidad del título
    private String title;

    // El lenguaje del libro (por ejemplo, inglés, español, etc.).
    private String language;

    // El número de descargas del libro.
    private Integer downloadCount;

    // @ManyToMany: Define una relación de muchos a muchos entre 'Book' y 'Author'.
    // fetch = FetchType.EAGER: La relación se carga inmediatamente (en el momento de la consulta). Puede cambiarse a LAZY para cargar la relación solo cuando se necesite.
    // @JoinTable: Especifica la tabla intermedia que maneja la relación muchos a muchos entre 'Book' y 'Author'.
    // 'name = "book_authors"': Nombre de la tabla intermedia.
    // 'joinColumns = @JoinColumn(name = "book_id")': Define la columna en la tabla intermedia que se refiere a la entidad 'Book'.
    // 'inverseJoinColumns = @JoinColumn(name = "author_id")': Define la columna en la tabla intermedia que se refiere a la entidad 'Author'.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_authors", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "book_id"), // Columna para el libro
            inverseJoinColumns = @JoinColumn(name = "author_id") // Columna para el autor
    )
    private List<Author> authors = new ArrayList<>(); // Lista de autores del libro

    // Getters y setters para acceder y modificar los valores de los atributos privados.

    // Getter para la lista de autores: Retorna la lista de autores asociados con este libro.
    public List<Author> getAuthors() {
        return authors;
    }

    // Setter para la lista de autores: Establece la lista de autores asociados con este libro.
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    // Getter para 'id': Retorna el valor del identificador único de este libro.
    public Long getId() {
        return id;
    }

    // Setter para 'id': Establece el valor del identificador único de este libro.
    public void setId(Long id) {
        this.id = id;
    }

    // Getter para 'title': Retorna el título del libro.
    public String getTitle() {
        return title;
    }

    // Setter para 'title': Establece el título del libro.
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter para 'language': Retorna el idioma del libro.
    public String getLanguage() {
        return language;
    }

    // Setter para 'language': Establece el idioma del libro.
    public void setLanguage(String language) {
        this.language = language;
    }

    // Getter para 'downloadCount': Retorna el número de descargas del libro.
    public Integer getDownloadCount() {
        return downloadCount;
    }

    // Setter para 'downloadCount': Establece el número de descargas del libro.
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}

