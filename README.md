# 🗣️ ForoHub API

ForoHub es una API REST desarrollada con **Spring Boot** que simula el backend de un foro de discusión. Permite registrar, listar, actualizar y eliminar tópicos asociados a distintos cursos, con validaciones de integridad de datos.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.5.11**
- **Spring Data JPA** — persistencia con Hibernate
- **Spring Validation** — validación de entradas
- **MySQL** — base de datos relacional
- **Flyway** — migraciones de base de datos
- **Lombok** — reducción de boilerplate
- **Maven** — gestión de dependencias

---

## 📁 Estructura del proyecto

```
src/main/java/com/aldocursos/forohub/
├── ForohubApplication.java
├── modules/
│   ├── Estado.java              # Enum: ABIERTO, CERRADO, SOLUCIONADO
│   ├── Curso.java               # Enum: JAVA, PYTHON, JAVASCRIPT, etc.
│   ├── ValidacionException.java # Excepción personalizada
│   ├── topico/
│   │   ├── Topico.java
│   │   ├── TopicoController.java
│   │   ├── TopicoService.java
│   │   ├── TopicoRepository.java
│   │   ├── DatosRegistroTopico.java
│   │   ├── DatosActualizacionTopico.java
│   │   └── DatosListadoTopico.java
│   └── usuario/
│       ├── Usuario.java
│       └── UsuarioRepository.java
└── shared/
    └── exceptions/
        └── GestorDeErrores.java
```

---

## ⚙️ Configuración

### Variables de entorno

Antes de ejecutar el proyecto, configura las siguientes variables de entorno con los datos de tu instancia MySQL:

| Variable         | Descripción                             |
|------------------|-----------------------------------------|
| `DB_HOST_MYSQL`  | URL del host (ej. `jdbc:mysql://localhost:3306`) |
| `DB_NAME_MYSQL`  | Nombre de la base de datos              |
| `DB_USER_MYSQL`  | Usuario de la base de datos             |
| `DB_PASSWORD`    | Contraseña de la base de datos          |

### Base de datos

Flyway ejecuta automáticamente las migraciones al iniciar la aplicación. La migración inicial (`V1__create-tables-usuarios-topicos.sql`) crea las tablas `usuarios` y `topicos`.

---

## 📡 Endpoints

Base URL: `/topicos`

| Método   | Ruta          | Descripción                        |
|----------|---------------|------------------------------------|
| `POST`   | `/topicos`    | Registrar un nuevo tópico          |
| `GET`    | `/topicos`    | Listar todos los tópicos (paginado)|
| `GET`    | `/topicos/{id}` | Obtener detalle de un tópico     |
| `PUT`    | `/topicos/{id}` | Actualizar título y/o mensaje    |
| `DELETE` | `/topicos/{id}` | Eliminar un tópico               |

### Ejemplo de registro (`POST /topicos`)

```json
{
  "titulo": "¿Cómo usar Streams en Java?",
  "mensaje": "Tengo dudas sobre el uso de map y filter",
  "status": "ABIERTO",
  "idAutor": 1,
  "curso": "JAVA"
}
```

### Paginación y ordenamiento

El listado soporta paginación automática. Por defecto retorna 10 tópicos ordenados por `fechaCreacion` de forma ascendente. Puedes modificarlo con los parámetros `page`, `size` y `sort`.

---

## ✅ Validaciones

- No se permite registrar un tópico con el mismo **título y mensaje** que uno ya existente.
- El `idAutor` debe corresponder a un usuario registrado en la base de datos.
- Los campos `titulo`, `mensaje`, `status`, `idAutor` y `curso` son obligatorios al registrar.

---

## 🛡️ Manejo de errores

La clase `GestorDeErrores` centraliza el manejo de excepciones y retorna respuestas HTTP apropiadas:

- `404 Not Found` — cuando no se encuentra un tópico o usuario por ID.
- `400 Bad Request` — cuando hay errores de validación en los campos o se intenta duplicar un tópico.

---

## ▶️ Cómo ejecutar

```bash
# Clona el repositorio
git clone <url-del-repo>
cd forohub

# Configura las variables de entorno y ejecuta
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

---

## 📝 Cursos disponibles

`JAVA`, `PYTHON`, `JAVASCRIPT`, `CSHARP`, `RUBY`, `PHP`, `SWIFT`, `KOTLIN`, `GO`, `RUST`

## 📊 Estados de un tópico

`ABIERTO`, `CERRADO`, `SOLUCIONADO`
