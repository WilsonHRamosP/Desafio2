package com.desafio2;

import com.desafio2.model.Book;
import com.desafio2.model.Author;
import com.desafio2.repository.BookRepository;
import com.desafio2.repository.AuthorRepository;
import com.desafio2.service.BookService; // Añadir importación de BookService
import com.desafio2.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

// La anotación @SpringBootApplication indica que esta es la clase principal para la configuración de la aplicación Spring Boot.
@SpringBootApplication
public class Desafio2Application implements CommandLineRunner {

	// Inyección de dependencias de los servicios y repositorios necesarios para el funcionamiento de la aplicación.
	@Autowired
	private GutendexService gutendexService; // Servicio para interactuar con la API de Gutendex.

	@Autowired
	private BookRepository bookRepository; // Repositorio para interactuar con la base de datos de libros.

	@Autowired
	private AuthorRepository authorRepository; // Repositorio para interactuar con la base de datos de autores.

	@Autowired
	private BookService bookService; // Servicio para manejar la lógica relacionada con los libros.

	// El método main inicia la aplicación Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(Desafio2Application.class, args);
	}

	// Este método verifica si los autores ya están registrados en la base de datos y, si no, los guarda.
	private List<Author> verificarYGuardarAutores(List<Author> authors) {
		List<Author> result = new ArrayList<>();
		// Recorremos la lista de autores proporcionada.
		for (Author author : authors) {
			// Verificamos si el autor ya existe en la base de datos por su nombre.
			Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
			if (existingAuthor.isPresent()) {
				result.add(existingAuthor.get()); // Si el autor existe, lo agregamos a la lista de resultados.
			} else {
				result.add(authorRepository.save(author)); // Si el autor no existe, lo guardamos en la base de datos.
			}
		}
		return result; // Retornamos la lista de autores (ya existentes o recién guardados).
	}

	// El método 'run' es ejecutado al inicio de la aplicación debido a la implementación de CommandLineRunner.
	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in); // Creamos un objeto Scanner para leer la entrada del usuario.

		// Bucle que muestra las opciones disponibles en el menú.
		while (true) {
			System.out.println("Elija la opción a través de su número:");
			System.out.println("1- Buscar libro por título.");
			System.out.println("2- Lístar libros registrados.");
			System.out.println("3- Lístar autores registrados.");
			System.out.println("4- Lístar autores vivos en un determinado año.");
			System.out.println("5- Lístar libros por idioma.");
			System.out.println("0- Salir.");
			System.out.print("Selecciona una opción para continuar: ");

			// Leemos la opción seleccionada por el usuario.
			int option = scanner.nextInt();
			scanner.nextLine(); // Limpiamos el buffer de entrada.

			// Utilizamos un 'switch' para ejecutar la opción correspondiente.
			switch (option) {
				case 1:
					buscarYRegistrarLibro(scanner); // Buscar y registrar un libro por su título.
					break;
				case 2:
					// Llamamos al repositorio para listar todos los libros registrados en la base de datos.
					bookRepository.findAll().forEach(this::imprimirLibro);
					break;
				case 3:
					// Listar todos los autores registrados en la base de datos.
					listarAutoresRegistrados();
					break;
				case 4:
					// Llamamos al método para listar los autores vivos en un año específico.
					listarAutoresVivosEnAno(scanner);
					break;
				case 5:
					// Listar libros filtrados por idioma.
					listarLibrosPorIdioma(scanner);
					break;
				case 0:
					// Finalizar la ejecución de la aplicación.
					System.out.println("Aplicación finalizada.");
					return; // Salir del bucle y terminar el programa.
				default:
					// Si el usuario selecciona una opción no válida.
					System.out.println("Opción inválida. Intente nuevamente.");
			}
		}
	}

	// Método para buscar un libro por su título y registrarlo si no existe en la base de datos.
	private void buscarYRegistrarLibro(Scanner scanner) {
		System.out.print("Ingrese el título del libro: ");
		String title = scanner.nextLine().trim();

		// Verificamos si el libro ya está registrado en la base de datos.
		Optional<Book> existingBook = bookRepository.findByTitleIgnoreCase(title);
		if (existingBook.isPresent()) {
			// Si el libro ya existe, lo mostramos y no lo registramos nuevamente.
			System.out.println("El libro ya está registrado en la base de datos:");
			System.out.println("No se puede registrar más de una vez.");
			imprimirLibro(existingBook.get());
			return; // Salir del método si el libro ya está registrado.
		}

		// Si el libro no existe en la base de datos, buscamos el libro usando la API de Gutendex.
		Optional<Book> bookFromApi = gutendexService.fetchBookByTitle(title);
		if (bookFromApi.isPresent()) {
			Book book = bookFromApi.get();

			// Verificamos y guardamos los autores del libro.
			if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
				book.setAuthors(verificarYGuardarAutores(book.getAuthors()));
			}

			try {
				// Guardamos el libro en la base de datos.
				bookRepository.save(book);
				System.out.println("Libro registrado exitosamente:");
				imprimirLibro(book);
			} catch (Exception e) {
				// En caso de error al guardar el libro.
				System.err.println("Error al guardar el libro: " + e.getMessage());
			}
		} else {
			// Si el libro no se encuentra en la API.
			System.out.println("Libro no encontrado.");
		}
	}

	// Método para listar todos los autores registrados en la base de datos.
	private void listarAutoresRegistrados() {
		List<Author> authors = authorRepository.findAll();

		if (authors.isEmpty()) {
			System.out.println("No hay autores registrados.");
		} else {
			// Recorremos y mostramos los datos de cada autor.
			for (Author author : authors) {
				System.out.println("Autor: " + author.getName());
				System.out.println("Fecha de nacimiento: " + (author.getBirthYear() != null ? author.getBirthYear() : "Desconocida"));
				System.out.println("Fecha de fallecimiento: " + (author.getDeathYear() != null ? author.getDeathYear() : "Aún vivo o desconocida"));

				// Obtener los libros asociados a este autor.
				List<Book> books = bookRepository.findByAuthorsContaining(author);
				if (books.isEmpty()) {
					System.out.println("Libros: No hay libros registrados para este autor.");
				} else {
					System.out.print("Libros: ");
					books.forEach(book -> System.out.print(book.getTitle() + ", "));
					System.out.println();
				}
				System.out.println();
			}
		}
	}

	// Método para listar autores vivos en un año específico.
	private void listarAutoresVivosEnAno(Scanner scanner) {
		System.out.print("Ingrese el año: ");
		int year = scanner.nextInt();

		// Llamamos al servicio BookService para obtener los autores vivos en el año especificado.
		List<Author> livingAuthors = bookService.listLivingAuthorsInYear(year);

		if (livingAuthors.isEmpty()) {
			System.out.println("No se encontraron autores vivos en el año " + year);
		} else {
			System.out.println("Autores vivos en el año " + year + ":");
			for (Author author : livingAuthors) {
				System.out.println("Autor: " + author.getName());
				System.out.println("Fecha de nacimiento: " + (author.getBirthYear() != null ? author.getBirthYear() : "Desconocida"));
				System.out.println("Fecha de fallecimiento: " + (author.getDeathYear() != null ? author.getDeathYear() : "Aún vivo o desconocida"));

				// Obtener los libros de cada autor.
				List<Book> books = bookRepository.findByAuthorsContaining(author);
				if (books.isEmpty()) {
					System.out.println("Libros: No hay libros registrados para este autor.");
				} else {
					System.out.print("Libros: ");
					books.forEach(book -> System.out.print(book.getTitle() + ", "));
					System.out.println();
				}

				System.out.println(); // Espaciado entre autores
			}
		}
	}

	// Método para listar libros filtrados por idioma.
	private void listarLibrosPorIdioma(Scanner scanner) {
		// Mapa de códigos de idioma a descripciones.
		Map<String, String> languageDescriptions = new HashMap<>();
		languageDescriptions.put("en", "Inglés");
		languageDescriptions.put("es", "Español");
		languageDescriptions.put("pt", "Portugués");
		languageDescriptions.put("fr", "Francés");

		// Obtener los idiomas disponibles en la base de datos.
		List<String> languages = bookRepository.findDistinctLanguages();

		if (languages.isEmpty()) {
			System.out.println("No hay idiomas disponibles en la base de datos.");
			return;
		}

		// Mostrar los idiomas disponibles.
		System.out.println("Idiomas disponibles:");
		for (String language : languages) {
			String description = languageDescriptions.getOrDefault(language, "Desconocido");
			System.out.println(language + " - " + description);
		}

		// Solicitar al usuario que ingrese un idioma.
		System.out.print("Ingrese el idioma: ");
		String language = scanner.nextLine().trim();

		// Buscar libros en el idioma ingresado.
		List<Book> books = bookRepository.findByLanguage(language);
		if (books.isEmpty()) {
			System.out.println("No se encontraron libros para el idioma: " + language);
		} else {
			System.out.println("Libros disponibles en el idioma " + language + ":");
			books.forEach(this::imprimirLibro);
		}
	}

	// Método para imprimir los detalles de un libro.
	private void imprimirLibro(Book book) {
		System.out.println("---Libro---");
		System.out.println("Título: " + book.getTitle());

		// Mostrar los autores del libro.
		if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
			System.out.print("Autores: ");
			for (Author author : book.getAuthors()) {
				System.out.print(author.getName() + " ");
			}
			System.out.println(); // Nueva línea después de la lista de autores.
		} else {
			System.out.println("Autores: No registrados");
		}

		System.out.println("Idioma: " + book.getLanguage());
		System.out.println("Número de descargas: " + book.getDownloadCount());
	}
}

