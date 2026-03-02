# 🗣️ ForoHub API

ForoHub es una API REST desarrollada con **Spring Boot** que simula el backend de un foro de discusión. Permite registrar, listar, actualizar y eliminar tópicos asociados a distintos cursos, con validaciones de integridad de datos y autenticación mediante JWT.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.5.11**
- **Spring Data JPA** — persistencia con Hibernate
- **Spring Security** — seguridad y autenticación
- **Spring Validation** — validación de entradas
- **MySQL** — base de datos relacional
- **Flyway** — migraciones de base de datos
- **Lombok** — reducción de boilerplate
- **Auth0 Java JWT 4.4.0** — generación y verificación de tokens JWT
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
│       ├── UsuarioRepository.java
│       ├── AutenticacionController.java
│       ├── AutenticacionService.java
│       └── DatosAutenticacion.java
└── shared/
    ├── exceptions/
    │   └── GestorDeErrores.java
    └── security/
        ├── SecurityConfigurations.java
        ├── SecurityFilter.java
        ├── TokenService.java
        └── DatosTokenJWT.java
```

---

## ⚙️ Configuración

### Variables de entorno

Antes de ejecutar el proyecto, configura las siguientes variables de entorno:

| Variable         | Descripción                                                  |
|------------------|--------------------------------------------------------------|
| `DB_HOST_MYSQL`  | URL del host (ej. `jdbc:mysql://localhost:3306`)             |
| `DB_NAME_MYSQL`  | Nombre de la base de datos                                   |
| `DB_USER_MYSQL`  | Usuario de la base de datos                                  |
| `DB_PASSWORD`    | Contraseña de la base de datos                               |
| `JWT_KEY`        | Clave secreta para firmar los tokens JWT (default: `123456`) |
| `JWT_EXPIRACION` | Horas de vigencia del token JWT (default: `2`)               |

> ⚠️ Se recomienda cambiar `JWT_KEY` por un valor seguro en entornos de producción.

### Base de datos

Flyway ejecuta automáticamente las migraciones al iniciar la aplicación. La migración inicial (`V1__create-tables-usuarios-topicos.sql`) crea las tablas `usuarios` y `topicos`.

---

## 🔐 Autenticación

La API utiliza **JWT (JSON Web Token)** para proteger sus rutas. Solo el endpoint `/login` es público; todos los demás requieren un token válido.

### 1. Iniciar sesión

**`POST /login`**

```json
{
  "email": "usuario@ejemplo.com",
  "password": "tu_contraseña"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 2. Usar el token

Incluye el token en el header `Authorization` de cada petición protegida:

```
Authorization: Bearer <token>
```

El token expira según el valor configurado en `JWT_EXPIRACION` (horas).

---

## 📡 Endpoints

### Autenticación

| Método | Ruta     | Descripción              | Protegido |
|--------|----------|--------------------------|-----------|
| `POST` | `/login` | Iniciar sesión y obtener JWT | ❌ No |

### Tópicos — Base URL: `/topicos`

| Método   | Ruta              | Descripción                         | Protegido |
|----------|-------------------|-------------------------------------|-----------|
| `POST`   | `/topicos`        | Registrar un nuevo tópico           | ✅ Sí     |
| `GET`    | `/topicos`        | Listar todos los tópicos (paginado) | ✅ Sí     |
| `GET`    | `/topicos/{id}`   | Obtener detalle de un tópico        | ✅ Sí     |
| `PUT`    | `/topicos/{id}`   | Actualizar título y/o mensaje       | ✅ Sí     |
| `DELETE` | `/topicos/{id}`   | Eliminar un tópico                  | ✅ Sí     |

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
- `403 Forbidden` — cuando se intenta acceder a un endpoint protegido sin un token válido.

---

## ▶️ Cómo ejecutar

```bash
# Clona el repositorio
git clone 
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