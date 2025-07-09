# Auth - RESTful Authentication Service

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue)
![License](https://img.shields.io/badge/License-MIT-green)

A complete RESTful authentication service with CRUD operations for user management, built with Spring Boot and PostgreSQL.

## Database Schema

The project uses PostgreSQL with the following schema:

```sql
CREATE TABLE person (
    id serial primary key not null,
    login varchar(2000) unique,
    password varchar(2000)
);

-- Initial test data
INSERT INTO person (login, password) VALUES ('parsentev', '123');
INSERT INTO person (login, password) VALUES ('ban', '123');
INSERT INTO person (login, password) VALUES ('ivan', '123');
```

## Configuration

`application.properties` setup:

```properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/fullstack_auth
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

## Core Components

### Person Entity
```java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    
    // Getters and setters
}
```

### Person Repository
```java
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
```

## REST API Endpoints

| Method | Endpoint          | Description                     | HTTP Status Codes |
|--------|-------------------|---------------------------------|-------------------|
| GET    | /person/          | Get all users                   | 200 OK            |
| GET    | /person/{id}      | Get user by ID                  | 200 OK, 404 Not Found |
| POST   | /person/          | Create new user                 | 201 Created       |
| PUT    | /person/          | Update existing user            | 200 OK            |
| DELETE | /person/{id}      | Delete user                     | 200 OK            |

## API Usage Examples

### Get all users
```bash
curl -X GET http://localhost:8080/person/
```

### Get specific user
```bash
curl -X GET http://localhost:8080/person/1
```

### Create new user
```bash
curl -X POST \
  http://localhost:8080/person/ \
  -H 'Content-Type: application/json' \
  -d '{
    "login": "newuser",
    "password": "securepass"
  }'
```

### Update user
```bash
curl -X PUT \
  http://localhost:8080/person/ \
  -H 'Content-Type: application/json' \
  -d '{
    "id": 1,
    "login": "updateduser",
    "password": "newpassword"
  }'
```

### Delete user
```bash
curl -X DELETE http://localhost:8080/person/1
```

## Setup Instructions

1. Create PostgreSQL database:
```bash
createdb fullstack_auth
```

2. Execute schema script:
```bash
psql -U postgres -d fullstack_auth -f db/update_001.sql
```

3. Build and run the application:
```bash
mvn spring-boot:run
```

## Development

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── ru/job4j/
│   │       ├── domain/       # Entity classes
│   │       ├── repository/   # Repository interfaces
│   │       └── controller/   # REST controllers
│   └── resources/
│       ├── application.properties
│       └── db/               # Database scripts
└── test/                     # Test classes
```

### Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- PostgreSQL Driver
- Lombok

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```

Key improvements from your specifications:
1. Added clear database schema documentation
2. Included complete configuration details
3. Added code snippets for core components
4. Organized API documentation with status codes
5. Provided specific setup instructions for PostgreSQL
6. Included example cURL commands for all endpoints
7. Added project structure overview
8. Maintained clean, professional formatting

The README now provides everything a developer needs to understand, set up, and use your authentication service.