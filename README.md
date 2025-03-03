![https://wakatime.com/badge/github/marianarossi/ceramics-ecommerce-API.svg](https://wakatime.com/badge/github/marianarossi/ceramics-ecommerce-API.svg)


# E-commerce API Backend

This repository contains a RESTful API built with Spring Boot, serving as the backend for an e-commerce application. The API leverages the full power of the Spring ecosystem to deliver a secure, maintainable, and scalable solution.

FrontEnd is available at https://github.com/marianarossi/ceramics-eCommerce-ReactJs

## Key Technologies & Frameworks

- **Spring Boot & Spring Framework:**  
  - **Spring Boot** provides auto-configuration, embedded server support, and a convention-over-configuration approach that simplifies setup and development.
  - **Spring MVC** is used to design robust RESTful web services.
  - **Spring Data JPA** abstracts and simplifies database interactions using the Java Persistence API (JPA).
  - **Spring Security** is integrated to secure endpoints through robust authentication and authorization, including JWT-based security.

- **Modern Java & Maven:**  
  - Developed with **Java 21**, ensuring the use of the latest language features and performance improvements.
  - Managed with **Maven** for standardized dependency management and a consistent build process.

- **Additional Libraries:**  
  - **Java JWT:** Handles the creation and validation of JSON Web Tokens for secure authentication.
  - **ModelMapper:** Facilitates seamless conversion between domain models and Data Transfer Objects (DTOs).
  - **Lombok:** Reduces code by automatically generating getters, setters, and constructors.
  - **Hibernate Validator:** Implements Java Bean Validation to enforce data constraints on incoming requests.
  - **Apache HttpClient5 (test scope):** Supports HTTP-based integration testing.

## Architecture & Project Structure

The application follows a layered architecture that cleanly separates concerns:

- **Controllers:**  
  REST controllers receive HTTP requests, delegate processing to services, and return appropriate responses.
  
- **Services:**  
  Business logic and transactional operations are encapsulated within service classes. A generic CRUD service implementation is provided to reduce code duplication.
  
- **Repositories:**  
  Data access is managed via Spring Data JPA repositories, which offer built-in CRUD operations and query capabilities.
  
- **Models & DTOs:**  
  Domain entities are mapped to database tables using JPA annotations. Data Transfer Objects (DTOs) are used to decouple internal models from API payloads, with ModelMapper handling conversions.
  
- **Security:**  
  JWT-based authentication and authorization are implemented with Spring Security. Passwords are securely hashed using BCrypt, and the application includes custom error handling for both validation and security exceptions.
  
- **Testing:**  
  A Test-Driven Development (TDD) approach is used to validate endpoints. Integration tests leverage Spring Boot’s testing framework with TestRestTemplate to ensure proper API behavior.

## Configuration & Setup

- **Database:**  
  By default, the project uses an in-memory H2 database, with configuration specified in `application.yml`. The H2 console is enabled at `/h2-console` for development and debugging. You can easily switch to other databases (e.g., PostgreSQL, MySQL) by updating the configuration.

- **Running the Application:**  
  1. **Clone the repository.**
  2. **Build the project using Maven:**
     ```
     mvn clean install
     ```
  3. **Run the application:**
     ```
     mvn spring-boot:run
     ```
  4. **Access the H2 console (if needed):**  
     Navigate to [http://localhost:8080/h2-console](http://localhost:8080/h2-console) and use the URL `jdbc:h2:mem:testdb`.

## Custom Error Handling & Validation

- **Validation:**  
  Incoming API requests are validated using Hibernate Validator annotations (e.g., @NotNull, @Size, @Pattern) to ensure data integrity.
  
- **Error Handling:**  
  Custom error handling is implemented with classes such as `ApiError`, `ErrorHandler`, and `ExceptionHandlerAdvice` to return consistent and concise error responses.

## JWT-Based Security

- **Authentication:**  
  Users authenticate by sending a POST request (with JSON credentials) to the `/login` endpoint. Successful authentication returns a JWT.
  
- **Authorization:**  
  All secured endpoints require the JWT to be passed in the `Authorization` header (prefixed with `Bearer`). The token is validated on each request to ensure proper authorization.

## Features

- **Product Management:**  
  - List all available products.  
  - View detailed information for each product in separate product pages.  
  - Filter products by category.

- **Shopping Cart:**  
  - Add products to the cart (available to both authenticated and non-authenticated users).  
  - Edit product quantities or remove products from the cart.

- **User Management & Authentication:**  
  - Register new users with unique usernames.  
  - Authenticate users before purchase finalization.

- **Checkout Process:**  
  - Display a confirmation screen for delivery address and items in the cart.  
  - Allow users to select an existing address or register a new one.  
  - Process payment information and finalize the order.

- **Order History:**  
  - Enable users to view their past orders with detailed information on purchased items.

- **Additional Capabilities:**  
  - Support for multiple delivery addresses per user.  
  - Integration with external services (e.g., address lookup using ViaCEP).

