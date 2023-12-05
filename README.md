# User-Management-System
The User Management System is a comprehensive backend-focused RESTful API, showcasing a range of backend concepts, software development best practices, advanced architectural approaches, and integration with databases and front-end systems. This system is designed for robust user management, featuring extensive CRUD operations, advanced security measures, role-based access control, custom exception handling, and thorough documentation.

## Features

### **CRUD Operations** 
- **Create**: Add a new user to the system with unique email identification.
- **Read**: Retrieve user details based on their ID or name. Additionally, fetch a list of all registered users.
- **Update**: Modify existing user details using their unique ID.
- **Delete**: Remove a user from the system using their unique ID.

### **Exception Handling** 
- Customized responses for various exceptions to ensure clear communication of issues to the client.
- Detailed error messages for instances such as duplicate email entries or when attempting to access non-existent users.

### **Javadoc Documentation** 
- Comprehensive documentation provided for each class and method to ensure clarity and ease of understanding for developers.
- Annotations and clear documentation on the purpose and usage of each REST endpoint.

### **Logging** 
- Detailed logging provided at different levels (INFO, WARN) for better troubleshooting and system monitoring.
- Traceable logs for every significant action, such as user creation, update, or deletion.

### **Data Integrity**
- Enforced email uniqueness to ensure each user has a distinct email address.
- JPA integration for seamless object-relational mapping and data persistence to the "user_info" database table.

### **Response Structuring**
- Standardized response structure using `ResponseHandler` to maintain consistency in all API responses, which includes a status message and relevant data.

### **Role-Based Access Control** 
- Implemented RBAC to enhance security and provide differentiated access levels.
- Users can be assigned roles (e.g., "ADMIN", "USER") with specific permissions for various operations

### **Enhanced Security with Spring Security**
- Integrated Spring Security for authentication and authorization.
- Database-backed user storage for secure user data management.
- Password encryption using BCrypt for enhanced security.

## Technologies Used

### Core
- Java
- Spring Boot
- Maven

### Web, Data & Testing
- Spring Web
- Spring Data JPA / Hibernate ORM
- MySQL Connector
- H2 Database
- JUnit
- Mockito

### Documentation
- OpenAPI and Swagger
- JavaDocs

### Validation
- Spring Validation

### Utility
- Lombok

## Usage

### **Get a User by ID**

Retrieve a user by their ID.

curl -X GET http://localhost:8080/userAPI/id/{id}

Replace `{id}` with the desired user's ID.

### **Get a User by Name**

Retrieve a user by their name.

curl -X GET "http://localhost:8080/userAPI/name/{name}"

Replace `{name}` with the desired user's name. Ensure the name is URL encoded if it contains spaces or special characters.

### **Get All Users**

Retrieve all users.

curl -X GET http://localhost:8080/userAPI

### **Create a User**

Add a new user to the system.

curl -X POST http://localhost:8080/userAPI/createUser \
     -H "Content-Type: application/json" \
     -d '{
           "name": "John Doe",
           "email": "john.doe@example.com",
           "age": 25
         }'

### **Update a User**

Update an existing user's details.

curl -X PUT http://localhost:8080/userAPI/updateUser \
     -H "Content-Type: application/json" \
     -d '{
           "id": 1,
           "name": "Johnathan Doe",
           "email": "johnathan.doe@example.com",
           "age": 26
         }'

### **Delete a User**

Delete a user by their ID.

curl -X DELETE http://localhost:8080/userAPI/deleteUser/{id}

Replace `{id}` with the ID of the user you wish to delete.

## Author
Zachary Graceman
