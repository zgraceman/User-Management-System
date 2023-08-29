# User-Management-System
The User Management System is a backend-focused RESTful API implementation showcasing essential backend concepts, software development best practices, advanced architectural approaches, database and front-end integration. It is designed to manage users, featuring comprehensive CRUD operations, custom exception handling, and thorough documentation.

## Features

### 1. **CRUD Operations** 
- **Create**: Add a new user to the system with unique email identification.
- **Read**: Retrieve user details based on their ID or name. Additionally, fetch a list of all registered users.
- **Update**: Modify existing user details using their unique ID.
- **Delete**: Remove a user from the system using their unique ID.

### 2. **Exception Handling** 
- Customized responses for various exceptions to ensure clear communication of issues to the client.
- Detailed error messages for instances such as duplicate email entries or when attempting to access non-existent users.

### 3. **Javadoc Documentation** 
- Comprehensive documentation provided for each class and method to ensure clarity and ease of understanding for developers.
- Annotations and clear documentation on the purpose and usage of each REST endpoint.

### 4. **Logging** 
- Detailed logging provided at different levels (INFO, WARN) for better troubleshooting and system monitoring.
- Traceable logs for every significant action, such as user creation, update, or deletion.

### 5. **Data Integrity**
- Enforced email uniqueness to ensure each user has a distinct email address.
- JPA integration for seamless object-relational mapping and data persistence to the "user_info" database table.

### 6. **Response Structuring**
- Standardized response structure using `ResponseHandler` to maintain consistency in all API responses, which includes a status message and relevant data.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- JUnit
- Mockito
- AssertJ
- H2 Database
- Maven
- Lombok

## Usage

### **1. Get a User by ID**

Retrieve a user by their ID.

curl -X GET http://localhost:8080/userAPI/id/{id}

Replace `{id}` with the desired user's ID.

### **2. Get a User by Name**

Retrieve a user by their name.

curl -X GET "http://localhost:8080/userAPI/name/{name}"

Replace `{name}` with the desired user's name. Ensure the name is URL encoded if it contains spaces or special characters.

### **3. Get All Users**

Retrieve all users.

curl -X GET http://localhost:8080/userAPI

### **4. Create a User**

Add a new user to the system.

curl -X POST http://localhost:8080/userAPI/createUser \
     -H "Content-Type: application/json" \
     -d '{
           "name": "John Doe",
           "email": "john.doe@example.com",
           "age": 25
         }'

### **5. Update a User**

Update an existing user's details.

curl -X PUT http://localhost:8080/userAPI/updateUser \
     -H "Content-Type: application/json" \
     -d '{
           "id": 1,
           "name": "Johnathan Doe",
           "email": "johnathan.doe@example.com",
           "age": 26
         }'

### **6. Delete a User**

Delete a user by their ID.

curl -X DELETE http://localhost:8080/userAPI/deleteUser/{id}

Replace `{id}` with the ID of the user you wish to delete.

## Author
Zachary Graceman
