##  FilmHub
To develop a platform where users can write movie reviews and leave comments.

###  Target Users
Movie enthusiasts and cinema lovers.

##  Technologies
- **Backend:** Spring Boot (Java)
- **Database:** PostgreSQL
- **Security:** Spring Security
- **Documentation:** Swagger
- **Unit Testing:** JUnit, Mockito

##  MVP Structure
The project is developed using a minimum viable product (MVP) approach, which allows for rapid delivery of core functionality and facilitates future enhancements based on user feedback.

<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-03%20at%2000.22.10.png" alt="Database Diagrams" width="500"/>

##  Layered Architecture
- **Controller:** API endpoints are defined here.
- **Service:** Business logic is implemented here.
- **Model:** Contains entities such as Film, User, and Comment.

##  Database Design
###  Film Table
- **id:** (Primary Key)
- **title:** (String)
- **description:** (String)
- **release_date:** (Date)
- **rating:** (Double)
- **Comments:** (String)

###  User Table
- **id:** (Primary Key)
- **username:** (String)
- **password:** (String - hashed)
- **email:** (String)
- **role:** (String)
- **Comments:** (String)

###  Comment Table
- **id:** (Primary Key)
- **user_id:** (Foreign Key)
- **film_id:** (Foreign Key)
- **comment:** (String)
- **created_at:** (Date)
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2022.02.18.png" alt="Database Diagrams" width="500"/>

###   API Endpoints
####   User Management
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`
- `GET /users`
- `POST /users`
- `GET /users/username`
- `GET /users/email`
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2021.53.15.png" alt="Database Diagrams" width="500"/>

###  Film Management
- `GET /films/{id}`
- `POST /films`
- `GET /films`
- `GET /films/search`
- `GET /films/rating`
- `PUT /films/{id}`
- `DELETE /films/{id}`
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2021.53.20.png" alt="Database Diagrams" width="500"/>

###  Comment Management
- `POST /comments`
- `GET /comments/{id}`
- `GET /comments/user/{userId}`
- `GET /comments/user/{userId}/film/{filmId}`
- `GET /comments/film/{filmId}`
- `GET /comments`
- `PUT /comments/{id}`
- `DELETE /comments/{id}`
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2021.53.25.png" alt="Database Diagrams" width="500"/>

##  Spring Security Integration
An authentication mechanism allows users to add and update only their own comments. Admin and User roles with session management using JWT (JSON Web Token).

##  Writing Unit Tests
Unit tests for the Service and Controller layers have been created using JUnit and Mockito.
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2021.54.06.png" alt="Database Diagrams" width="500"/>
<img src="https://github.com/caglaozbb/FilmHub/blob/main/img/Screenshot%202024-10-04%20at%2021.54.19.png" alt="Database Diagrams" width="500"/>

##  Swagger Integration
Use Swagger UI to display endpoint information and provide documentation for the APIs.

##  Project Development
1. **Creating the Spring Boot Project:** A basic Spring Boot project was created using Spring Initializr, and necessary dependencies were added.
2. **Configuring Database Connection:** The `application.properties` file was updated with the database connection details.
3. **Creating Model, Repository, and Service Classes:** Film, User, and Comment models were created; repository interfaces were defined, and service classes were implemented.
4. **Writing Controller Classes:** Controller classes were created to define the API endpoints.
5. **Integrating Spring Security:** Spring Security configurations were made for user registration and login processes.
6. **Writing Unit Tests:** Test scenarios were added to the service and controller layers.
7. **Using Docker:** Dockerfile and docker-compose.yml files were created to dockerize the project.

