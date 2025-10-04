# Customer Service API

A REST API for managing patient information, converted from SOAP to REST architecture.

## Features

- **Patient Management**: Create and retrieve patient information
- **Excel Form Upload**: Upload patient forms via Excel files
- **REST API**: Clean REST endpoints with proper HTTP status codes
- **Validation**: Input validation with detailed error messages
- **Database**: JPA/Hibernate with MySQL support

## API Endpoints

### Health Check
- `GET /api/v1/ping` - Health check endpoint

### Patient Management
- `POST /api/v1/patients` - Create a new patient
- `GET /api/v1/patients` - Get all patients
- `GET /api/v1/patients/{id}` - Get patient by ID (to be implemented)
- `POST /api/v1/patients/upload` - Upload patient form via Excel file

## Running the Application

1. **Prerequisites**:
   - Java 21
   - Maven 3.6+
   - MySQL database

2. **Database Setup**:
   - Create a MySQL database named `soulware_customersdb`
   - Update `src/main/resources/application.properties` with your database credentials

3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Test the API**:
   ```bash
   # Health check
   curl http://localhost:8080/api/v1/ping
   
   # Create a patient
   curl -X POST http://localhost:8080/api/v1/patients \
     -H "Content-Type: application/json" \
     -d '{
       "firstNames": "John",
       "paternalSurname": "Doe",
       "maternalSurname": "Smith",
       "documentType": "DNI",
       "documentNumber": "12345678",
       "phone": "+1234567890",
       "birthDate": "1990-01-01",
       "receiptType": "INVOICE"
     }'
   
   # Get all patients
   curl http://localhost:8080/api/v1/patients
   ```

## Project Structure

```
src/main/java/com/soulware/platform/customerservice/
├── cs/
│   ├── application/          # Application services
│   ├── domain/              # Domain models and services
│   ├── infrastructure/      # Persistence layer
│   └── interfaces/
│       └── rest/           # REST API layer
│           ├── controllers/ # REST controllers
│           ├── dto/        # Data Transfer Objects
│           └── mappers/    # DTO mappers
└── shared/                 # Shared infrastructure
```

## Architecture

This project follows Domain-Driven Design (DDD) principles with a clean architecture:

- **Domain Layer**: Contains business logic and entities
- **Application Layer**: Contains use cases and application services
- **Infrastructure Layer**: Contains database repositories and external services
- **Interface Layer**: Contains REST controllers and DTOs

## Dependencies

- Spring Boot 3.5.0
- Spring Web (REST API)
- Spring Data JPA
- MySQL Connector
- Apache POI (Excel processing)
- Lombok
- Validation API


