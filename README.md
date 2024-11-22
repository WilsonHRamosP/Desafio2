desafio2.
Practicando-Spring-Boot-y-PostgreSQL.

Este es un proyecto de ejemplo utilizando Spring Boot y PostgreSQL como base de datos. El proyecto implementa una API que permite gestionar libros y autores. Los usuarios pueden buscar libros por título, idioma y consultar autores registrados. Además, el sistema recupera información adicional desde la API de Gutendex.

Tecnologías
*Java 17: Lenguaje de programación.
*Spring Boot 3.x: Framework para crear aplicaciones Java basadas *en Spring.
*Spring Data JPA: Implementación de JPA para acceder y manipular *la base de datos.
*PostgreSQL: Base de datos utilizada para almacenar libros y autores.
*Thymeleaf: Motor de plantillas para renderizar vistas HTML.
*Lombok: Biblioteca que reduce el código repetitivo en Java (por ejemplo, getters, setters, etc.).
*Jackson: Para el manejo de JSON en la aplicación.

Requisitos
1. Java 17 instalado.
2. PostgreSQL configurado y corriendo.
3. Un IDE como IntelliJ IDEA o Eclipse (con soporte para Spring Boot).
4. Conexión a internet para acceder a la API de Gutendex.

Instalación
1. Clona el repositorio:
git clone https://github.com/tu_usuario/desafio2.git
cd desafio2

2.Abre el proyecto en tu IDE.

3.Configura las credenciales de tu base de datos PostgreSQL en el archivo. src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/desafio2
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=FALSE
spring.jackson.date-format=yyyy-MM-dd
spring.jackson.time-zone=GMT
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=INFO
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
server.port=8081

4. (Opcional) Crea la base de datos desafio2 en PostgreSQL si no está creada:
psql -U postgres -c "CREATE DATABASE desafio2;"

5.Ejecuta la aplicación:
./mvnw spring-boot:run

6. La aplicación estará disponible en http://localhost:8081.

Funcionalidades
1. Buscar libro por título: Permite buscar un libro ingresando su título. Si el libro no está registrado, se intenta obtenerlo desde la API de Gutendex y luego se guarda en la base de datos.
2. Listar libros registrados: Muestra todos los libros registrados en la base de datos.
3. Listar autores registrados: Muestra todos los autores registrados.
4. Listar autores vivos en un año específico: Permite consultar los autores que estaban vivos en un determinado año.
5. Listar libros por idioma: Permite filtrar los libros registrados por su idioma.

Estructura del Proyecto
desafio2/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── desafio2/
│   │   │   │       ├── model/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       └── Desafio2Application.java
│   │   ├── resources/
│   │   │   └── application.properties
└── pom.xml

Contribuciones
Si deseas contribuir al proyecto, sigue estos pasos:
1. Haz un fork del repositorio.
2. Crea una rama con la nueva funcionalidad (git checkout -b feature/nueva-funcionalidad).
3. Realiza tus cambios y haz commits.
4. Realiza un pull request desde tu rama.

Licencia
Este proyecto está licenciado bajo la Licencia MIT.

Autor 
WilsonHRamosP.

Notas adicionales
1. Spring Boot Configuration: El archivo application.properties contiene la configuración de la base de datos y de Hibernate. Asegúrate de tener configurado PostgreSQL y de cambiar las credenciales si es necesario.
2. API de Gutendex: La aplicación consulta la API pública de Gutendex para obtener información adicional sobre libros si no están registrados.