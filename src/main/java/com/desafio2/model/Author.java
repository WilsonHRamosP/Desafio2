package com.desafio2.model;

import jakarta.persistence.*;
import java.util.List;

// @Entity: Indica que esta clase es una entidad JPA, es decir, una clase que se mapea a una tabla en la base de datos.
@Entity
// @Table: Especifica el nombre de la tabla en la base de datos donde se almacenarán los datos de los autores.
@Table(name = "authors")
public class Author {

    // @Id: Marca el campo 'id' como la clave primaria de la tabla.
    // @GeneratedValue: Indica que el valor de 'id' se genera automáticamente.
    // strategy = GenerationType.IDENTITY: Indica que el valor del 'id' se generará mediante un autoincremento en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column: La anotación @Column se usa para mapear un atributo a una columna en la base de datos.
    // nullable = false: La columna 'name' no puede ser nula en la base de datos (es un campo obligatorio).
    @Column(nullable = false) // El nombre es obligatorio
    private String name;

    // @Column(name = "birth_year"): Asocia el atributo 'birthYear' con la columna 'birth_year' en la base de datos.
    // El año de nacimiento del autor.
    @Column(name = "birth_year")
    private Integer birthYear;

    // @Column(name = "death_year"): Asocia el atributo 'deathYear' con la columna 'death_year' en la base de datos.
    // El año de fallecimiento del autor (puede ser nulo si el autor está vivo).
    @Column(name = "death_year")
    private Integer deathYear;

    // @ManyToMany: Indica que existe una relación de muchos a muchos entre 'Author' y 'Book'.
    // mappedBy: Indica que la relación está mapeada por el atributo 'authors' de la clase 'Book'.
    // Esto significa que la clase 'Author' es la parte inversa de la relación (es decir, no es la que contiene la clave foránea).
    @ManyToMany(mappedBy = "authors") // Relación inversa de ManyToMany
    private List<Book> books;

    // Getters y setters: Son métodos que permiten acceder y modificar los valores de los atributos privados de la clase.

    // Getter para 'id': Retorna el valor de 'id'.
    public Long getId() {
        return id;
    }

    // Setter para 'id': Establece el valor de 'id'.
    public void setId(Long id) {
        this.id = id;
    }

    // Getter para 'name': Retorna el valor de 'name'.
    public String getName() {
        return name;
    }

    // Setter para 'name': Establece el valor de 'name'.
    public void setName(String name) {
        this.name = name;
    }

    // Getter para 'birthYear': Retorna el valor de 'birthYear'.
    public Integer getBirthYear() {
        return birthYear;
    }

    // Setter para 'birthYear': Establece el valor de 'birthYear'.
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    // Getter para 'deathYear': Retorna el valor de 'deathYear'.
    public Integer getDeathYear() {
        return deathYear;
    }

    // Setter para 'deathYear': Establece el valor de 'deathYear'.
    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    // Getter para 'books': Retorna la lista de libros asociados a este autor.
    public List<Book> getBooks() {
        return books;
    }

    // Setter para 'books': Establece la lista de libros asociados a este autor.
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}


