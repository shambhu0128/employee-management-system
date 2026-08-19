# Employee Management System

A production-ready Employee Management System REST API built using **Java**, **Spring Boot**, **Spring Security (JWT)**, **MySQL**, **Redis**, **Apache Kafka**, **Docker**, and **Swagger/OpenAPI** — deployed live on **Render** with **Aiven-managed MySQL, Redis, and Kafka**.

**Live Demo:** https://employee-management-system-cpej.onrender.com

## Features

- Secure JWT Authentication
- Role-Based Authorization (ADMIN & USER)
- Employee CRUD Operations
- Input Validation
- Global Exception Handling
- Pagination & Sorting
- Redis Caching (`@Cacheable`/`@CacheEvict`) for reduced database load
- Event-driven notifications via Apache Kafka (producer/consumer pipeline)
- Swagger API Documentation
- Dockerized Application
- MySQL Database Integration
- Layered Architecture (Controller-Service-Repository)
- Deployed on Render with Aiven-managed MySQL, Redis, and Kafka over SSL/TLS

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- MySQL
- Redis (Aiven-managed, SSL/TLS)
- Apache Kafka (Aiven-managed, SASL_SSL)
- JWT (JSON Web Token)
- Maven
- Docker & Docker Compose
- Render (Cloud Hosting)
- Swagger / OpenAPI

---

## Project Structure
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
---

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Generate JWT Token |

### Employee APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/employees` | Create Employee |
| GET | `/employees` | Get All Employees |
| GET | `/employees/{id}` | Get Employee by ID |
| PUT | `/employees/{id}` | Update Employee |
| DELETE | `/employees/{id}` | Delete Employee |
| GET | `/employees/page` | Paginated Employees |
| GET | `/employees/filter` | Filter by Department & Salary |
| GET | `/employees/department/{department}` | Get by Department |
| GET | `/employees/search/{name}` | Search by Name |
| GET | `/employees/high-salary/{salary}` | Employees Above Salary Threshold |

Supports:

- Pagination
- Sorting

---

## Authentication

The application uses **JWT Authentication**.

Default Users

### Admin

Username
admin
Password
Test@1234
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
http://localhost:8080
Swagger UI
http://localhost:8080/swagger-ui/index.html
---

## Cloud Deployment

Deployed live on **Render**, integrated with:
- **Aiven MySQL** — managed relational database
- **Aiven Valkey (Redis)** — SSL-secured caching layer
- **Aiven Kafka** — SASL_SSL-secured event streaming

CA certificate trust for Redis is handled by importing Aiven's CA certificate directly into the JVM trust store at container startup (see `entrypoint.sh`), ensuring secure SSL connectivity in the containerized cloud environment.

---

## Project Highlights

- RESTful API Design
- Secure Authentication & Authorization
- Event-Driven Architecture with Kafka
- Redis Caching for Performance Optimization
- Clean Layered Architecture
- DTO Mapping
- Validation
- Exception Handling
- Dockerized & Cloud-Deployed
- Production-ready Project Structure

---

## Future Enhancements

- Unit & Integration Testing
- CI/CD Pipeline (GitHub Actions)
- Frontend Application (Angular/React)
- Logging & Monitoring
- Email Notifications (real, not simulated)

---

## Author

**Shubham Singh**

Java Backend Developer