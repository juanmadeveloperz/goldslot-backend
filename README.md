# GoldSlot Backend - Spring Boot

SaaS multiplataforma para gestión de autoescuelas y academias de conducción.

## Stack

- Java 17 + Spring Boot 4.1.0
- Spring Data JPA + Hibernate
- PostgreSQL
- Lombok
- Maven

## Entities

- Usuario: Cliente/Escuela
- Alumno: Estudiante
- Lesson: Clase de conducción
- DailyNotes: Notas de lección
- NotaPersonal: Bloc de notas del profesor

## Endpoints

### Usuarios
- POST /api/usuarios
- GET /api/usuarios
- GET /api/usuarios/{id}
- PUT /api/usuarios/{id}
- DELETE /api/usuarios/{id}

### Alumnos
- POST /api/alumnos
- GET /api/alumnos
- GET /api/alumnos/{id}
- GET /api/alumnos/usuario/{usuarioId}
- PUT /api/alumnos/{id}
- DELETE /api/alumnos/{id}

### Lessons
- POST /api/lessons
- GET /api/lessons
- GET /api/lessons/{id}
- GET /api/lessons/usuario/{usuarioId}
- GET /api/lessons/alumno/{alumnoId}
- PUT /api/lessons/{id}
- DELETE /api/lessons/{id}

### Daily Notes
- POST /api/daily-notes
- GET /api/daily-notes
- GET /api/daily-notes/{id}
- GET /api/daily-notes/lesson/{lessonId}
- PUT /api/daily-notes/{id}
- DELETE /api/daily-notes/{id}

### Notas Personales
- POST /api/notas-personales
- GET /api/notas-personales
- GET /api/notas-personales/{id}
- GET /api/notas-personales/usuario/{usuarioId}
- GET /api/notas-personales/usuario/{usuarioId}/fecha/{fecha}
- PUT /api/notas-personales/{id}
- DELETE /api/notas-personales/{id}

## Roadmap

- Autenticación JWT
- Módulo ITv
- Módulo Exams
- Reportes avanzados
- Stripe integration
- Frontend React/Angular
- AWS ECS Deploy
- GitHub Actions CI/CD

## Autor

Juan M. Espiño | Backend Developer | Java + Spring Boot + AWS

v1.0 - June 2026
