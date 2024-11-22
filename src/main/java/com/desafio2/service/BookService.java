package com.desafio2.service;

import com.desafio2.model.Author;
import com.desafio2.model.Book;
import com.desafio2.repository.AuthorRepository;
import com.desafio2.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service: Indica que esta clase es un servicio en la capa de negocio de la aplicación.
// Los servicios se encargan de la lógica del negocio y de interactuar con los repositorios.
@Service
public class BookService {

    // Se inyectan los repositorios de 'Book' y 'Author' a través del constructor.
    // Esto permite que la clase acceda a los datos de los libros y autores en la base de datos.
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // Constructor de la clase BookService. Utiliza inyección de dependencias para inicializar los repositorios.
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Listar todos los libros: Este método llama al repositorio de libros para obtener todos los libros de la base de datos.
    // Retorna una lista de objetos 'Book'.
    public List<Book> listBooks() {
        // Llama al método 'findAll()' de 'bookRepository' para obtener todos los libros almacenados en la base de datos.
        return bookRepository.findAll();
    }

    // Listar libros por idioma: Este método recibe un idioma como parámetro y utiliza el repositorio de libros
    // para obtener todos los libros que coincidan con ese idioma.
    public List<Book> listBooksByLanguage(String language) {
        // Llama al método 'findByLanguage(language)' de 'bookRepository' para obtener todos los libros en ese idioma específico.
        return bookRepository.findByLanguage(language);
    }

    // Listar todos los autores: Este método llama al repositorio de autores para obtener todos los autores en la base de datos.
    // Retorna una lista de objetos 'Author'.
    public List<Author> listAuthors() {
        // Llama al método 'findAll()' de 'authorRepository' para obtener todos los autores almacenados en la base de datos.
        return authorRepository.findAll();
    }

    // Listar autores vivos en un año determinado: Este método recibe un año como parámetro y llama al repositorio de autores
    // para obtener todos los autores que estaban vivos en ese año. Utiliza el método 'findLivingAuthorsInYear(year)' del repositorio de autores.
    public List<Author> listLivingAuthorsInYear(int year) {
        // Llama al método 'findLivingAuthorsInYear(year)' de 'authorRepository' para obtener los autores vivos en el año especificado.
        return authorRepository.findLivingAuthorsInYear(year); // Llamando al método del repositorio con la lógica corregida
    }
}

