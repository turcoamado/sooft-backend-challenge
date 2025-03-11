# Backend technical challenge for SOOFT Technology

## Project Description
This project implements a RESTful service for managing companies and their transactions. The service provides endpoints to:

1. Retrieve a list of companies with transactions in the last 30 days.
2. Validate if a company already exists by CUIT or name before registration.
3. Register a new company in the database.

## Solution Architecture
The service is built using Spring Boot and Kotlin. The database is managed using PostgreSQL, and object mapping is handled with JPA and MapStruct.

## Dependencies and Requirements
To run the project, ensure you have:

- Java 21 or higher
- Gradle (for dependency management and build automation)
- Spring Boot 3.x
    - spring-boot-starter-web (for building API REST)
    - spring-boot-starter-data-jpa (for database interaction)
- PostgreSQL database
- Kotlin Standard Library and Reflection
- MapStruct (for DTO mapping)
- JUnit 5 (for unit testing)
- MockK (for mocking dependencies in unit testing)

## Installation and Execution
1. Clone the repository
    ```
    git clone https://github.com/turcoamado/sooft-backend-challenge.git
    cd sooft-backend-challenge
    git checkout main
    ```

2. Configure the database

   Ensure you have a running PostgreSQL instance and create the database manually:

   ```
   CREATE DATABASE SooftChallenge;
   ```

3. Configure the environment

   Modify [application.properties](src/main/resources/application.properties) with your PostgreSQL credentials if needed.

4. Build and run tests
    ```
   ./gradlew clean test
   ```

5. Run the application
    ```
   ./gradlew bootRun
    ```
   When you run the application, data in [data.sql](src/main/resources/sql/data.sql) file will be added into the database created.

## Consume the API Endpoints

The service exposes three endpoints:

1. Get companies with transactions in the last month
   ```
   GET /companies/transactions-last-month
   ```
   - Returns a list of companies that had transactions in the last 30 days. 
   - If no companies are found, an empty list is returned.
   
2. Get companies registered in the last month
   ```
   GET /companies/registered-last-month
   ```
   - Returns a list of companies that were registered in the last 30 days.
   - If no companies are found, an empty list is returned.

3. Register a new company
   ```
   POST /companies/register
   ```

   - Request Body:
   ```json
   {
      "cuit": "30-12345678-1",
      "companyName": "Example Company",
      "registrationDate": "2024-03-10T12:00:00"
   }
   ```
   - Response: 
   ```json
    {
       "id": 1,
       "cuit": "30-12345678-1",
       "companyName": "Example Company",
       "registrationDate": "2024-03-10T12:00:00"
    }
   ```
     - Returns `201 Created` if the company is successfully registered.
   
## NOTES
- The definition of "last month" was assumed to mean the last 30 days from the date of the request. This value is configurable in application.properties:
   ```
   company.query.lastDays=30
   ```
- The service only exposes the three detailed endpoints; there is no other business logic.
- The ```debitAccount``` and ```creditAccount``` attributes in ```Transactions``` are treated as simple Strings. They may be useful in the future if a specific workflow for transactions is defined and if we need to track where transactions originate and where they are directed.
- The ```CuitValidator``` class was implemented following the regulations of the Republic of Argentina to determine whether a CUIT is valid or not.
  - [CUIT](https://es.wikipedia.org/wiki/Clave_%C3%9Anica_de_Identificaci%C3%B3n_Tributaria)
  - [Control code](https://es.wikipedia.org/wiki/C%C3%B3digo_de_control)