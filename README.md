# Kinguin Internship Task
This Spring Boot application is designed as part of the Kinguin internship program. It provides a system for managing book loans for a library. The application uses MongoDB to store data about customers, books, and loans.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Usage](#usage)
- [Example API Requests](#example-api-requests)
- [Example API Responses](#example-api-responses)
- [Code formatting](#code-formatting)


## Features
- **Loan Management**: Users can loan or return books using a simple interface.
- **Customer and Book Details**: Detailed information about customers and books is maintained and can be accessed as needed.
- **Loan Tracking**: Keeps track of all loans, including the customer and book details associated with each loan.
- **Swagger UI**: The application provides a Swagger UI for easy access to the API documentation. The Swagger UI is available at `http://localhost:8080/swagger-ui.html`.
- **DataLoader**: The application uses a DataLoader class to populate the database with sample data located in the resources folder. The DataLoader class is executed automatically when the application starts. If you want to enable DataLoader, set the `app.dataloader.enable` property to `true` in the application.properties file.

## Technologies Used
- Java
- Spring Boot
- MongoDB
 - **Note**: Settings for MongoDB are located in the application.properties file. You can change the database name, host, port, and other settings as needed.
- Gradle

## API Endpoints
**Disclaimer**: I've used only ID for identifying resources, as it is the most common practice. I've provided search endpoints for books and customers to make it easier to find resources by different parameters. I thought it would be useful for the frontend to have such functionality. Then, after finding the desired resource, the frontend can use the ID to perform further operations.
### Books
- **GET /books**: Get all books.
- **GET /books/{id}**: Get a book by ID.
- **POST /books**: Add a new book.
- **PUT /books/{id}**: Update a book.
- **DELETE /books/{id}**: Delete a book.
- **GET /books/search**: Search for books by title, author, publisher, genre, or ISBN.

### Customers
- **GET /customers**: Get all customers.
- **GET /customers/{id}**: Get a customer by ID.
- **POST /customers**: Add a new customer.
- **PUT /customers/{id}**: Update a customer.
- **DELETE /customers/{id}**: Delete a customer.
- **GET /customers/search**: Search for customers by first name, last name, or library card number.

### Loans
- **GET /loans**: Get all loans.
- **POST /loans**: For lending or returning a book, depending on the book's current status.
- **GET /loans/customer/{customerId}**: Get all loans for a specific customer.
- **GET /loans/customer/{customerId}/active**: Get all active loans for a specific customer.
- **GET /loans/customer/{customerId}/returned**: Get all returned loans for a specific customer.

## Getting Started

### Prerequisites
- JDK 11 or later
- MongoDB (for storing data)
- Gradle (for building the project)

### Installation
To set up the project locally, follow these steps:

1. Clone the repository:
   ```sh
   git clone https://github.com/KuchnowskiP/KinguinInternshipTask.git
    ```
2. Navigate to the project directory:
3. Build the project using Gradle:
   ```sh
   gradle build
   ```
4. Run the application:
   ```sh
    gradle bootRun
    ```
   
### Usage
Once the application is running, you can access the API endpoints using tools like cURL, Postman or test them with the Swagger UI. The Swagger UI is available at `http://localhost:8080/swagger-ui.html`.
All endpoints are available with the prefix `http://localhost:8080/api/v1/`.

## Example API Requests

Example will be shown using Windows version of cURL. If you are using different operating system, please refer to the proper documentation.

### GET example: Search for books by title, author, publisher, genre, and ISBN
```sh
curl -X GET "http://localhost:8080/api/v1/books/search?title={title}&author={author}&publisher={publisher}&genre={genre}&isbn={isbn}" -H "accept: application/json"
```

Please replace parameters in curly braces with the desired values. 
In search queries, every parameter is optional.

If you want to search for The Great Gatsby by all parameters, you can use the following command:

```sh
curl -X GET "http://localhost:8080/api/v1/books/search?title=The%20Great%20Gatsby&author=F.%20Scott%20Fitzgerald&publisher=Scribner&genre=Classic%20Fiction&isbn=9780743273565" -H "accept: application/json"
```

If you want to search for the same book, for example, by title only, you can use the following command:
```sh
curl -X GET "http://localhost:8080/api/v1/books/search?title=The%20Great%20Gatsby" -H "accept: application/json"
```

Omitting all parameters will return all books in the database.

### POST example: Add a new book
```sh
curl -X POST "http://localhost:8080/api/v1/books?author={author}&genre={genre}&isbn={isbn}&publisher={publisher}&title={title}" -H "accept: */*"
```

Please replace parameters in curly braces with the desired values.

### PUT example: Update a book
```sh
curl -X PUT "http://localhost:8080/api/v1/books/{id}?author={author}&genre={genre}&isbn={isbn}&publisher={publisher}&title={title}" -H "accept: */*"
```

Please replace `{id}` with the ID of the book you want to update and parameters in curly braces with the desired values.

### DELETE example: Delete a book
```sh
curl -X DELETE "http://localhost:8080/api/v1/books/{id}" -H "accept: */*"
```

Please replace `{id}` with the ID of the book you want to delete.

**Note**: If you would like to see more examples, please refer to the Swagger UI documentation available at `http://localhost:8080/swagger-ui.html`.

## Example API Responses
### Success Response for GET all books
```json
[
  {
    "id": "60b1b3b3b3b3b3b3b3b3b3b3",
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "publisher": "Scribner",
    "genre": "Classic Fiction",
    "isbn": "9780743273565"
  },
  {
    "id": "60b1b3b3b3b3b3b3b3b3b3b",
    "title": "To Kill a Mockingbird",
    "author": "Harper Lee",
    "publisher": "J.B. Lippincott & Co.",
    "genre": "Classic Fiction",
    "isbn": "9780061120084"
  }
]
```


### Error Response for GET book by ID
```json
{
  "timestamp": "2021-05-29T14:00:00.000+00:00",
  "status": 400,
  "error": "Not Found",
  "message": "Book not found",
  "path": "/api/v1/books/60b1b3b3b3b3b3b3b3b3b3b3"
}
```

## Code formatting
The project uses the Google Java Style Guide for code formatting.
