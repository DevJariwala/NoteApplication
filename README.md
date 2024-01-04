# **Note Application**

## **Overview**

The Note Application is a simple and scalable RESTful API that allows users to create, read, update, and delete notes. Users can also share their notes with others and search for notes based on keywords.

## **Technologies Used**

### **Framework**

The application is built using the Spring Boot framework, chosen for its simplicity, ease of development, and robust ecosystem. Spring Boot provides a powerful and opinionated way to build Java applications, making it well-suited for building RESTful APIs.

### **Database**

The data is stored in a PostgreSQL database. PostgreSQL was chosen for its reliability, extensibility, and support for complex queries. It integrates seamlessly with Spring Data JPA, simplifying database operations.

### **3rd Party Tools**

- Spring Security: Used for authentication and authorization, ensuring secure access to the API endpoints.

- JWT (JSON Web Token): Implemented for token-based authentication, providing a secure way to authenticate users.

- Hibernate: Used as the Object-Relational Mapping (ORM) tool to interact with the database.

- JUnit and Mockito: Employed for writing unit tests and integration tests to ensure the reliability of the application.

## **Getting Started**

Follow these instructions to set up and run the Note Application on your local machine.

### **Prerequisites**

Ensure you have the following installed:

- Java 11 or higher
- Maven
- PostgreSQL

### **Database Configuration**

- Create a PostgreSQL database with the name NotesDB.
- Update the application.properties file with your database configuration.

### **Running the Application**

- Clone the repository:
  - git clone https://github.com/DevJariwala/NoteApplication.git
  - cd NoteApplication
- Build and run the application:
  - mvn clean install
  - mvn spring-boot:run

The application should be accessible at http://localhost:8080.

### **Running Tests**

Run the tests using the following command:
- mvn test

## **Additional Notes**

- The application uses Spring's default profiles, and the configurations can be adjusted in the application.properties file.
- Ensure that the necessary dependencies and plugins are configured in the pom.xml file.