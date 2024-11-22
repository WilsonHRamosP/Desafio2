package com.desafio2.service;

import com.desafio2.model.Book;
import com.desafio2.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// La anotación @Service indica que esta clase es un servicio en Spring, que maneja la lógica de negocio.
@Service
public class GutendexService {

    // 'RestTemplate' se utiliza para realizar peticiones HTTP a servicios externos (en este caso a la API de Gutendex).
    private final RestTemplate restTemplate;

    // Constructor de la clase. Utiliza inyección de dependencias para crear una instancia de 'RestTemplate' usando el 'RestTemplateBuilder'.
    // Este constructor es anotado con @Autowired, lo que permite a Spring inyectar una instancia del RestTemplateBuilder.
    @Autowired
    public GutendexService(RestTemplateBuilder restTemplateBuilder) {
        // Construir un 'RestTemplate' con el 'restTemplateBuilder'.
        this.restTemplate = restTemplateBuilder.build();
    }

    // Método para obtener un libro a partir de su título.
    public Optional<Book> fetchBookByTitle(String title) {
        // Construir la URL de la API de Gutendex utilizando el título del libro proporcionado como parámetro.
        String url = "https://gutendex.com/books/?search=" + title;

        // Realizar una petición GET a la API de Gutendex. 'exchange' permite realizar peticiones HTTP con diferentes métodos y parámetros.
        // El tipo de respuesta esperado es un 'ResponseEntity<Map<String, Object>>' que representa los datos devueltos por la API.
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        // Verificar si la respuesta contiene datos y si la lista de resultados no está vacía.
        if (response.getBody() != null && !((List<?>) response.getBody().get("results")).isEmpty()) {
            // Extraer los datos del primer libro (si hay más de un resultado, tomar solo el primero).
            Map<String, Object> bookData = ((List<Map<String, Object>>) response.getBody().get("results")).get(0);

            // Crear una instancia de la clase 'Book' y asignar los datos del libro extraídos de la respuesta de la API.
            Book book = new Book();
            book.setTitle((String) bookData.get("title")); // Asignar el título del libro.
            book.setLanguage(((List<String>) bookData.get("languages")).get(0)); // Asignar el idioma del libro (tomar el primer idioma).
            book.setDownloadCount((Integer) bookData.get("download_count")); // Asignar el número de descargas del libro.

            // Extraer los datos de los autores del libro.
            List<Map<String, Object>> authorsData = (List<Map<String, Object>>) bookData.get("authors");

            // Si hay autores disponibles, crear una lista de objetos 'Author' y asignarles los datos de la API.
            if (authorsData != null) {
                List<Author> authors = new ArrayList<>();
                // Recorrer la lista de autores y asignar sus datos a los objetos 'Author'.
                for (Map<String, Object> authorData : authorsData) {
                    Author author = new Author();
                    author.setName((String) authorData.get("name")); // Asignar el nombre del autor.
                    author.setBirthYear((Integer) authorData.get("birth_year")); // Asignar el año de nacimiento del autor.
                    author.setDeathYear((Integer) authorData.get("death_year")); // Asignar el año de fallecimiento del autor.
                    authors.add(author); // Añadir el autor a la lista de autores.
                }
                book.setAuthors(authors); // Asignar la lista de autores al libro.
            } else {
                // Si no se encuentran autores en la respuesta, imprimir un mensaje de advertencia.
                System.out.println("Autor no encontrado o no disponible en la API.");
            }

            // Retornar el libro envuelto en un Optional (indica que puede ser nulo).
            return Optional.of(book);
        }

        // Si no se encontraron resultados en la API, retornar Optional vacío.
        return Optional.empty();
    }
}



