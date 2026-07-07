# Employee Management System

A production-ready Employee Management System REST API built using **Java**, **Spring Boot**, **Spring Security (JWT)**, **MySQL**, **Docker**, and **Swagger/OpenAPI**.

## Features

- Secure JWT Authentication
- Role-Based Authorization (ADMIN & USER)
- Employee CRUD Operations
- Input Validation
- Global Exception Handling
- Pagination & Sorting
- Swagger API Documentation
- Dockerized Application
- MySQL Database Integration
- Layered Architecture (Controller-Service-Repository)

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- MySQL
- JWT (JSON Web Token)
- Maven
- Docker & Docker Compose
- Swagger / OpenAPI

---

## Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── security
├── auth
├── config
└── exception
```

---

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Generate JWT Token |

### Employee APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/employees` | Create Employee |
| GET | `/employees` | Get All Employees |
| GET | `/employees/{id}` | Get Employee by ID |
| PUT | `/employees/{id}` | Update Employee |
| DELETE | `/employees/{id}` | Delete Employee |

Supports:

- Pagination
- Sorting

---

## Authentication

The application uses **JWT Authentication**.

Default Users

### Admin

Username

```
admin
```

Password

```
admin123
```

### User

Username

```
user
```

Password

```
user123
```

---

## Running the Project

### Clone Repository

```bash
git clone https://github.com/shambhu0128/employee-management-system.git
```

### Run using Docker

```bash
docker compose up --build
```

Application

```
http://localhost:8080
```

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## Project Highlights

- RESTful API Design
- Secure Authentication & Authorization
- Clean Layered Architecture
- DTO Mapping
- Validation
- Exception Handling
- Dockerized Deployment
- Production-ready Project Structure

---

## Future Enhancements

- Unit & Integration Testing
- CI/CD Pipeline
- Cloud Deployment (AWS)
- Redis Caching
- Logging & Monitoring
- Email Notifications

---

## Author

**Shubham Singh**

Java Backend Developer