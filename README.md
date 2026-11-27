# Student Management System

A backend project built using **Java, Spring Boot, and MySQL** to manage student records and the courses associated with them.

I built this project to get more comfortable with Spring Boot and understand how backend applications work beyond basic API development. While working on it, I explored REST APIs, database operations, entity relationships, validation, pagination, sorting, and Spring Security.

## What can it do?

Here are the things I've implemented so far:

- Add new students and store their details in a MySQL database.
- View all students or fetch a particular student using their ID.
- Update student information and delete existing records.
- Add courses to individual students.
- Search students using their name or email.
- Search by partial name instead of requiring an exact match.
- Retrieve student records using pagination.
- Sort students alphabetically by name.
- Validate incoming data, including student names and email addresses.
- Handle validation errors through a centralized exception handler.
- Protect API endpoints using Spring Security and HTTP Basic Authentication.
- Test API responses and validation scenarios using JUnit and MockMvc.

## Technologies Used

| Technology | Where I used it |
|---|---|
| Java 21 | Application development |
| Spring Boot 4.1.1 | Main backend framework |
| Spring Web MVC | Creating REST APIs |
| Spring Data JPA | Database operations |
| Hibernate | Mapping Java objects to database tables |
| MySQL | Storing student and course data |
| Spring Security | Securing API endpoints |
| Jakarta Validation | Validating incoming data |
| Maven | Managing dependencies and building the project |
| JUnit 5 & MockMvc | Testing application functionality |

## How the project is structured

I have kept the application organized into different packages so that each part has its own responsibility.

```text
student-management/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/ucs067/studentmanagement/
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── HelloController.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── StudentRepository.java
│   │   │       │   └── CourseRepository.java
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       │
│   │   │       ├── Student.java
│   │   │       ├── Course.java
│   │   │       └── StudentManagementApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/example/ucs067/studentmanagement/
│               └── StudentManagementApplicationTests.java
│
├── pom.xml
└── README.md
```

## Understanding the Database Relationship

The project currently has two main entities: `Student` and `Course`.

**Student**

Each student has:
- An auto-generated ID
- Name
- Email
- A list of associated courses

**Course**

Each course has:
- An auto-generated ID
- Course name
- Credits
- A reference to the student it belongs to

The relationship between these entities is **One-to-Many**.

One student can have multiple courses, but each course belongs to one student.

I implemented this relationship using JPA annotations:

- `@OneToMany` in the Student entity.
- `@ManyToOne` in the Course entity.
- `CascadeType.ALL` to propagate persistence operations.
- `orphanRemoval = true` to remove orphaned courses.
- `@JsonManagedReference` and `@JsonBackReference` to handle JSON serialization of the bidirectional relationship.

## API Endpoints

All APIs run locally on `http://localhost:8080`.

### Student APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/students` | Get all students |
| POST | `/students` | Add a new student |
| GET | `/students/{id}` | Get student by ID |
| PUT | `/students/{id}` | Update student details |
| DELETE | `/students/{id}` | Delete a student |

### Search APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/students/search/name/{name}` | Search by exact name |
| GET | `/students/search/name/contains/{keyword}` | Search by partial name |
| GET | `/students/search/email/{email}` | Search by email |

### Pagination and Sorting

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/students/page` | Get students page by page |
| GET | `/students/sort/name` | Sort students by name |

For example:

```http
GET /students/page?page=0&size=5
```

To sort the paginated results:

```http
GET /students/page?page=0&size=5&sort=name,asc
```

### Course APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/courses` | Get all courses |
| POST | `/students/{studentId}/courses` | Add a course to a student |

### Other APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/hello` | Check the application response |
| GET | `/student` | Get a sample student object |

## Trying the APIs

You can use Postman to interact with the application.

**1. Add a student**

`POST /students`

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com"
}
```

**2. Add a course to that student**

`POST /students/1/courses`

```json
{
  "courseName": "Data Structures",
  "credits": 4
}
```

**3. Update student details**

`PUT /students/1`

```json
{
  "name": "Rahul Verma",
  "email": "rahul.verma@example.com"
}
```

**4. Get all students**

`GET /students`

**5. Delete a student**

`DELETE /students/1`

## Validation and Exception Handling

I used Jakarta Validation to make sure that the incoming student data is valid.

For example:

- Student name cannot be blank.
- Email cannot be blank.
- Email must follow a valid email format.

The `@Valid` annotation triggers validation when a new student is submitted.

I also added a `GlobalExceptionHandler` using `@RestControllerAdvice` to handle validation errors in one place instead of repeating exception-handling code in individual endpoints.

Invalid requests return `400 Bad Request` along with the corresponding field errors.

## Spring Security

I configured Spring Security using a custom `SecurityFilterChain`.

Currently:
- `/hello` is accessible without authentication.
- All other endpoints require authentication.
- HTTP Basic Authentication is enabled.
- CSRF protection is disabled in the current configuration.

For protected endpoints, credentials can be provided through Postman's Basic Auth option.

## Testing

I have also included tests using JUnit 5 and MockMvc.

The current test cases check:

- Whether the Spring application context loads successfully.
- Whether `/hello` returns the expected response.
- Whether invalid student data is rejected.
- Whether the correct validation error messages are returned.

To run the tests:

```bash
mvn test
```

## Running the Project Locally

**Requirements:**
- Java 21
- Maven
- MySQL

**1. Clone the repository**

```bash
git clone https://github.com/YOUR_USERNAME/student-management.git
cd student-management
```

**2. Create a MySQL database**

```sql
CREATE DATABASE student_management;
```

**3. Configure your database credentials**

Open `src/main/resources/application.properties` and add:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**4. Start the application**

```bash
mvn spring-boot:run
```

The application should now be available at:

`http://localhost:8080`

## What I Learned

Working on this project helped me understand several concepts that I had previously only studied theoretically.

Some of the things I got hands-on experience with include:

- How Spring Boot handles incoming HTTP requests.
- How controllers and repositories interact.
- How Spring Data JPA simplifies database operations.
- How to establish relationships between database entities.
- How pagination and sorting work in Spring Data.
- How to validate incoming API requests.
- How centralized exception handling works.
- How Spring Security controls access to endpoints.
- How to test REST APIs without manually sending every request through Postman.

## What's Next?

There are still things I'd like to improve as I continue learning Spring Boot.

Some ideas on my list are:
- Introducing a proper service layer.
- Using DTOs for request and response handling.
- Implementing JWT-based authentication.
- Adding role-based authorization.
- Integrating Swagger for API documentation.
- Connecting a frontend to the backend.

---

**Pratham Bansal**

[GitHub](https://github.com/bansalpratham) · [LinkedIn](https://www.linkedin.com/)

*Built while learning Spring Boot, exploring backend development, and getting a better understanding of how everything fits together.*
