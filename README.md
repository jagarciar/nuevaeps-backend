# 🏥 NuevaEPS Backend - API REST

Backend REST API desarrollado con **Spring Boot 3.2.1** y **Java 21** para el sistema de gestión de medicamentos de NuevaEPS.

## 📋 Contenido

- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [API Endpoints](#api-endpoints)
- [Autenticación JWT](#autenticación-jwt)
- [Base de Datos](#base-de-datos)
- [Testing](#testing)
- [Docker](#docker)

---

## ✅ Requisitos

- **Java 21** (JDK)
- **Maven 3.9+**
- **PostgreSQL 16**
- **Docker y Docker Compose** (para ejecución containerizada)

---

## 🚀 Instalación

### Compilar el Proyecto

```bash
cd nuevaeps-backend
mvn clean install
```

### Ejecutar Localmente

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Ejecutar con Docker

```bash
docker-compose up -d backend
```

---

## ⚙️ Configuración

### application.properties

```properties
# Server
server.port=8080
server.servlet.context-path=/

# Spring Profiles
spring.profiles.active=dev

# Database
spring.datasource.url=jdbc:postgresql://postgres:5432/nuevaeps_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

---

## 📁 Estructura del Proyecto

```
nuevaeps-backend/
├── src/
│   ├── main/
│   │   ├── java/com/nuevaeps/api/
│   │   │   ├── NuevaepsApiApplication.java        # Main
│   │   │   ├── config/                            # Configuración
│   │   │   ├── controller/                        # REST Controllers
│   │   │   │   ├── AuthController.java            # Autenticación
│   │   │   │   ├── MedicamentoController.java     # Medicamentos
│   │   │   │   └── SolicitudMedicamentoController.java  # Solicitudes
│   │   │   ├── dto/                               # Data Transfer Objects
│   │   │   │   ├── JwtResponse.java               # Respuesta JWT
│   │   │   │   ├── LoginRequest.java              # Request de login
│   │   │   │   └── RegisterRequest.java           # Request de registro
│   │   │   ├── model/                             # Entidades JPA
│   │   │   │   ├── Usuario.java                   # Usuarios
│   │   │   │   ├── Medicamento.java               # Medicamentos
│   │   │   │   └── SolicitudMedicamento.java      # Solicitudes de medicamentos
│   │   │   ├── repository/                        # Data Access Layer
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── MedicamentoRepository.java
│   │   │   │   └── SolicitudMedicamentoRepository.java
│   │   │   ├── security/                          # JWT & Seguridad
│   │   │   │   ├── JwtUtils.java                  # Utilidades JWT
│   │   │   │   ├── AuthTokenFilter.java           # Filtro JWT
│   │   │   │   ├── UserPrincipal.java             # UserDetailsService
│   │   │   │   ├── AuthEntryPointJwt.java         # Manejo de excepciones
│   │   │   │   └── WebSecurityConfig.java         # Configuración de seguridad
│   │   │   └── service/                           # Lógica de negocio
│   │   │       ├── UsuarioService.java
│   │   │       ├── MedicamentoService.java
│   │   │       └── SolicitudMedicamentoService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/nuevaeps/api/
│           └── controller/                        # Tests unitarios
│               ├── AuthControllerTest.java
│               ├── MedicamentoControllerTest.java
│               └── SolicitudMedicamentoControllerTest.java
├── pom.xml                                         # Dependencias Maven
├── Dockerfile                                      # Imagen Docker
└── README.md
```

---

## 🔌 API Endpoints

### Autenticación

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---|
| POST | `/api/v1/auth/login` | Iniciar sesión | ❌ |
| POST | `/api/v1/auth/register` | Registrar usuario | ❌ |

#### Ejemplo Login

**Request:**
```bash
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "admin",
  "accessToken": "eyJhbGciOiJIUzM4NCJ9...",
  "tokenType": "Bearer"
}
```

#### Ejemplo Crear Medicamento (ADMIN)

**Request:**
```bash
POST /api/v1/medicamentos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Loratadina"
}
```

**Response:**
```json
{
  "id": 5,
  "nombre": "Loratadina"
}
```

#### Ejemplo Actualizar Medicamento (ADMIN)

**Request:**
```bash
PUT /api/v1/medicamentos/5
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Loratadina 10mg"
}
```

**Response:**
```json
{
  "id": 5,
  "nombre": "Loratadina 10mg"
}
```

### Medicamentos

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---|
| GET | `/api/v1/medicamentos` | Obtener todos | ✅ |
| GET | `/api/v1/medicamentos/{id}` | Obtener por ID | ✅ |
| GET | `/api/v1/medicamentos/paginated` | Obtener paginado | ✅ |
| POST | `/api/v1/medicamentos` | Crear medicamento | ✅ ADMIN |
| PUT | `/api/v1/medicamentos/{id}` | Actualizar medicamento | ✅ ADMIN |

### Solicitudes de Medicamentos

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---|
| GET | `/api/v1/solicitudes-medicamentos` | Obtener todas | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/{id}` | Obtener por ID | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/usuario/{id}` | Por usuario | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/usuario/{id}/paginated` | Por usuario (paginado) | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/medicamento/{id}` | Por medicamento | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/medicamento/{id}/paginated` | Por medicamento (paginado) | ✅ |
| GET | `/api/v1/solicitudes-medicamentos/paginated` | Obtener paginado | ✅ |
| POST | `/api/v1/solicitudes-medicamentos` | Crear solicitud | ✅ |

---

## 📄 Paginación

### Parámetros

Los endpoints con `/paginated` soportan los siguientes parámetros de query:

| Parámetro | Tipo | Descripción | Por Defecto |
|-----------|------|-------------|------------|
| `page` | int | Número de página (0-indexed) | 0 |
| `size` | int | Cantidad de registros por página | 20 |
| `sort` | string | Campo para ordenar. Formato: `campo,asc\|desc` | Sin ordenamiento |

### Ejemplos

#### Obtener página 1 con 10 registros por página

```bash
GET /api/v1/medicamentos/paginated?page=0&size=10
Authorization: Bearer {token}
```

#### Obtener medicamentos ordenados por nombre (ascendente)

```bash
GET /api/v1/medicamentos/paginated?page=0&size=20&sort=nombre,asc
Authorization: Bearer {token}
```

#### Obtener solicitudes del usuario 3, página 2, 15 registros, ordenados por ID (descendente)

```bash
GET /api/v1/solicitudes-medicamentos/usuario/3/paginated?page=1&size=15&sort=id,desc
Authorization: Bearer {token}
```

#### Múltiples criterios de ordenamiento

```bash
GET /api/v1/solicitudes-medicamentos/paginated?page=0&size=20&sort=usuarioId,asc&sort=id,desc
Authorization: Bearer {token}
```

### Formato de Respuesta

La respuesta paginada incluye:

```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Paracetamol",
      ...
    },
    {
      "id": 2,
      "nombre": "Ibuprofeno",
      ...
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 50,
  "totalPages": 5,
  "last": false,
  "number": 0,
  "size": 10,
  "numberOfElements": 10,
  "first": true,
  "empty": false
}
```

### Campos de la Respuesta

| Campo | Descripción |
|-------|-------------|
| `content` | Array con los registros de la página actual |
| `totalElements` | Total de registros en la BD |
| `totalPages` | Total de páginas disponibles |
| `number` | Número de página actual (0-indexed) |
| `size` | Cantidad de registros por página |
| `numberOfElements` | Cantidad de registros en esta página |
| `first` | ¿Es la primera página? |
| `last` | ¿Es la última página? |
| `empty` | ¿La página está vacía? |
| `pageable` | Información de paginación |

---

## 🔐 Autenticación JWT

### Flujo

```
1. Usuario envía credenciales → POST /auth/login
2. Backend valida y genera JWT → Response con accessToken
3. Cliente guarda token en localStorage
4. Para requests protegidas → Header: Authorization: Bearer {token}
5. AuthTokenFilter valida JWT en cada request
6. Si token inválido → 401 Unauthorized
```

### Formato JWT

```
Header: {
  "alg": "HS384",
  "typ": "JWT"
}

Payload: {
  "sub": "username",
  "iat": 1234567890,
  "exp": 1234654290
}

Signature: HMACSHA384(header + payload, secret)
```

### Configuración

- **Algoritmo**: HS384
- **Expiración**: 24 horas
- **Secret**: Configurado en `application.properties`

---

## 💾 Base de Datos

La base de datos se crea automáticamente al iniciar la aplicación mediante el script [init-db.sql](../init-db.sql) que se ejecuta en PostgreSQL.

### Diagrama de Relaciones

```
┌──────────────────────────────┐
│         ROLES                │
├──────────────────────────────┤
│ id (PK) BIGSERIAL            │
│ nombre VARCHAR(50) UNIQUE    │
└──────────────────────────────┘
         ▲
         │
         │ (N:N)
         │
┌──────────────────────────────┐
│       USUARIOS               │
├──────────────────────────────┤
│ id (PK) BIGSERIAL            │
│ username VARCHAR(255) UNIQUE │
│ password VARCHAR(255)        │
└──────────────────────────────┘
         ▲
         │
         │ (1:N)
         │
┌──────────────────────────────────────────────────┐
│     SOLICITUDES_MEDICAMENTOS                     │
├──────────────────────────────────────────────────┤
│ id (PK) BIGSERIAL                                │
│ usuario_id (FK) ───────────┬─ USUARIOS.id        │
│ medicamento_id (FK) ───────┤                     │
│ numero_orden VARCHAR(255)   │                     │
│ direccion VARCHAR(500)      │                     │
│ telefono VARCHAR(20)        │                     │
│ correo_electronico VARCHAR  │                     │
│                             │                     │
│          (1:N)              │                     │
└──────────────────────────────────────────────────┘
                              │
                              │
         ┌────────────────────┘
         │
         ▼
┌──────────────────────────────┐
│    MEDICAMENTOS              │
├──────────────────────────────┤
│ id (PK) BIGSERIAL            │
│ nombre VARCHAR(255) NOT NULL │
└──────────────────────────────┘
```

### Tablas Detalladas

#### USUARIOS
Almacena credenciales de usuarios del sistema.

```sql
CREATE TABLE usuarios (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

-- Índice para búsquedas rápidas
CREATE INDEX idx_usuarios_username ON usuarios(username);
```

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---|---|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `username` | VARCHAR(255) | NOT NULL, UNIQUE | Nombre de usuario para login |
| `password` | VARCHAR(255) | NOT NULL | Contraseña hasheada con bcrypt |

**Usuarios de Prueba:**
- Username: `admin` | Password: `admin`
- Username: `usuario_test` | Password: `admin`

---

#### ROLES
Catálogo de roles del sistema para control de acceso.

```sql
CREATE TABLE roles (
  id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE
);
```

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---|---|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `nombre` | VARCHAR(50) | NOT NULL, UNIQUE | Nombre del rol (USER, ADMIN, MODERATOR) |

**Roles Disponibles:**
- `USER` - Usuario estándar, puede crear solicitudes
- `ADMIN` - Administrador, puede crear y actualizar medicamentos
- `MODERATOR` - Moderador, puede revisar solicitudes

---

#### USUARIOS_ROLES
Tabla intermedia para la relación Many-to-Many entre usuarios y roles.

```sql
CREATE TABLE usuarios_roles (
  usuario_id BIGINT NOT NULL,
  rol_id BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, rol_id),
  CONSTRAINT fk_usuarios_roles_usuario 
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  CONSTRAINT fk_usuarios_roles_rol 
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);
```

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---|---|
| `usuario_id` | BIGINT | NOT NULL, FK | Referencia a usuario |
| `rol_id` | BIGINT | NOT NULL, FK | Referencia a rol |
| (usuario_id, rol_id) | Composite | PRIMARY KEY | Identifica la asignación de rol |

**Relaciones:**
- FK (usuario_id) → usuarios(id) ON DELETE CASCADE
- FK (rol_id) → roles(id) ON DELETE CASCADE

---
Catálogo de medicamentos disponibles (solo lectura desde el frontend).

```sql
CREATE TABLE medicamentos (
  id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL
);

-- Índice para búsquedas por nombre
CREATE INDEX idx_medicamentos_nombre ON medicamentos(nombre);
```

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---|---|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `nombre` | VARCHAR(255) | NOT NULL | Nombre del medicamento |

**Medicamentos Iniciales:**
- Paracetamol
- Ibuprofeno
- Amoxicilina
- Metformina

---

#### SOLICITUDES_MEDICAMENTOS
Registro de solicitudes realizadas por usuarios para medicamentos.

```sql
CREATE TABLE solicitudes_medicamentos (
  id BIGSERIAL PRIMARY KEY,
  medicamento_id BIGINT NOT NULL,
  usuario_id BIGINT NOT NULL,
  numero_orden VARCHAR(255) NOT NULL UNIQUE,
  direccion VARCHAR(500) NOT NULL,
  telefono VARCHAR(20) NOT NULL,
  correo_electronico VARCHAR(255) NOT NULL,
  CONSTRAINT fk_solicitud_medicamento 
    FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id) ON DELETE CASCADE,
  CONSTRAINT fk_solicitud_usuario 
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índices para búsquedas rápidas
CREATE INDEX idx_solicitudes_medicamento ON solicitudes_medicamentos(medicamento_id);
CREATE INDEX idx_solicitudes_usuario ON solicitudes_medicamentos(usuario_id);
CREATE INDEX idx_solicitudes_numero_orden ON solicitudes_medicamentos(numero_orden);
```

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---|---|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `medicamento_id` | BIGINT | NOT NULL, FK | Referencia a medicamento |
| `usuario_id` | BIGINT | NOT NULL, FK | Referencia a usuario |
| `numero_orden` | VARCHAR(255) | NOT NULL, UNIQUE | Número de solicitud único |
| `direccion` | VARCHAR(500) | NOT NULL | Dirección de entrega |
| `telefono` | VARCHAR(20) | NOT NULL | Teléfono de contacto |
| `correo_electronico` | VARCHAR(255) | NOT NULL | Email de contacto |

**Relaciones:**
- FK (medicamento_id) → medicamentos(id) ON DELETE CASCADE
- FK (usuario_id) → usuarios(id) ON DELETE CASCADE

---

### Usuarios de Prueba

| Username | Contraseña | Roles | Descripción |
|----------|-----------|-------|---|
| admin | admin | ADMIN, USER | Administrador con permisos completos |
| usuario_test | admin | USER | Usuario estándar para pruebas |

**Nota:** Las contraseñas están hasheadas con bcrypt en la BD. El hash `$2a$10$dXJ3SW6G7P50eS4XW0JUXOUm8i8FGOy8sAWw3R.6yVl0vVnvVvI3y` corresponde a la contraseña "admin".

---

### Medicamentos Disponibles

| ID | Nombre |
|----|--------|
| 1 | Paracetamol |
| 2 | Ibuprofeno |
| 3 | Amoxicilina |
| 4 | Metformina |

---

### Conexión a Base de Datos

**Desde la aplicación:**
```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/nuevaeps_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Desde el host (psql):**
```bash
psql -h localhost -U postgres -d nuevaeps_db
```

**Credenciales PostgreSQL:**
- Host: `postgres` (contenedor) o `localhost` (host)
- Puerto: 5432
- Usuario: postgres
- Contraseña: postgres
- Base de datos: nuevaeps_db

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=AuthControllerTest

# Con reporte
mvn test -q
```

### Cobertura de Tests

- ✅ **AuthControllerTest** (4 tests)
  - Login exitoso
  - Login fallido
  - Registro exitoso
  - Errores de validación

- ✅ **MedicamentoControllerTest** (3 tests)
  - Obtener medicamentos
  - Crear medicamento
  - Eliminar medicamento

- ✅ **SolicitudMedicamentoControllerTest** (8 tests)
  - CRUD completo
  - Búsqueda por usuario
  - Validaciones

**Total: 15 tests pasando** ✅

---

## 🐳 Docker

### Build

```bash
docker build -t nuevaeps-backend:latest .
```

### Run

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/nuevaeps_db \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  nuevaeps-backend:latest
```

### Docker Compose

```bash
docker-compose up -d backend
```

---

## 🔧 Dependencias Principales

```xml
<!-- Spring Boot -->
<spring-boot-starter-web>
<spring-boot-starter-data-jpa>
<spring-boot-starter-security>

<!-- Database -->
<postgresql>
<hibernate-core>

<!-- JWT -->
<jjwt-api>
<jjwt-impl>
<jjwt-jackson>

<!-- Testing -->
<spring-boot-starter-test>
<junit-jupiter>
```

---

## 🚨 Troubleshooting

### Error: "Credenciales inválidas"
- Verificar que el usuario existe en BD
- Confirmar que la contraseña es correcta
- Registrar un nuevo usuario si es necesario

### Error: "401 Unauthorized"
- El JWT token ha expirado
- Revisar que el header Authorization está correcto
- Re-autenticarse haciendo login

### Error de conexión a BD
- Verificar que PostgreSQL está corriendo
- Revisar credenciales en application.properties
- Confirmar que la BD `nuevaeps_db` existe

---
