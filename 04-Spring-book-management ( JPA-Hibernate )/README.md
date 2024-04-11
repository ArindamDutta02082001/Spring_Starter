# This is a demo of springboot + hibernate JPA
<p>We follow the MVC model </p>
<p> controller  → service → repository → model<br/><br/>
controller → in this folder we will have the api endpoints , and service logic function are called here 
when endpoints are hit<br/>
@Restcontroller → the http endpoints are written under this function | it tells that the function
will contain http rest endpoints<br/><br/>
dto → this contains the request model to accept the http request <br/><br/>
service → the logic of backend after getting data in backend from endpoint | triggered when endpoint is hit <br/>
@Service → it tells that the function will contain the service logic of the rest endpoints <br/><br/>
entity/model → data models i.e attribute definition for the input to backend <br/><br/>
repository → creating data object & saving them to  database or in memory logic <br/>
@Repository → it tells that the function will contain the logic of the DB layer<br/><br/>
</p>

## ORM with JPA & Hibernate
**Object-Relational Mapping (ORM):** Utilizing frameworks like Hibernate to map Java objects to relational database tables.
<br/>
<br/>
Step 1: Dependency Setup
Include necessary dependencies like Dev Tools, Spring Web, H2 Database, SQL manager/driver, JDBC, JUnit, spring-data-jpa, and Oracle driver.
Add spring-data-jpa dependency.
<br/>
<br/>
Step 2: Configure Application Properties
Define JDBC properties: URL, username, password.
Set spring.jpa.hibernate.ddl-auto to specify schema handling.
<br/>
<br/>
Step 3: Define Entities and Columns
Use annotations like @Entity, @Id, @GeneratedValue, @Enumerated, @CreationTimestamp, @UpdateTimestamp, and @Column to define tables and columns in Java objects.
<br/>
<br/>
Step 4: Create Repository Interface
Create a repository interface by extending JpaRepository<Entity, Id_type>.
Implement custom queries using @Query annotation.
Optionally, use @Modifying and @Transactional for DML queries and data consistency.
<br/>
<br/>
Step 5: Establishing Relationships
Use annotations like @ManyToOne, @OneToMany, @ManyToMany, @OneToOne, and @JoinColumn to map relationships between tables (PK-FK relations).

## Custom SQL Queries
- Use @Query & @Modifying annotation to execute custom SQL queries.
- Queries can be in JPQL (Java Persistence Query Language) or Native query format.
<br/>

Examples:

**JPQL Format: ( preferred )**
@Query("select b from Book b where b.language = :lang")
public List<Book> findBookByLanguage(Language lang);
Native Query Format:

**Native Query**
@Query(value = "select * from book b where b.book_category = :cateo", nativeQuery = true)
public List<Book> findBookByCategory(int cateo);


## Api here
1. GET - `localhost:9000/book`  to get all the books  <br/>
2. GET - `localhost:9000/book/{ book id }`  to get the details of a book by id <br/>
3. GET - `localhost:9001/getbookbylang?lang=HINDI`  to get the details of a book by it language ( HINDI , ENGLISH , BENGALI) <br/>
4. GET - `localhost:9000/getbookbycateo?cateo=PHYSICS`  to get the details of a book by it cateogory <br/>
5. POST - `localhost:9000/book`  to create a new book

<pre>
  <code>
payload :
{
  "name": "Example Book New",
  "authorName": "John Doe",
  "publisherName": "Publisher ABC",
  "pages": "600",
  "bookCateogory": "PHYSICS",
  "language": "HINDI"
}

  response :
    {
    "UID": 2,
    "name": "Example Book New",
    "authorName": "John Doe",
    "publisherName": "Publisher ABC",
    "pages": "600",
    "bookCateogory": "PHYSICS",
    "language": "HINDI",
    "createdOn": 1706961767122,
    "updatedOn": 1706961767122
    }
  </code>
</pre>

6. PUT -  `localhost:9000/book?bid={ book id }` to update a book by its id

<pre>
  <code>
payload :
{
  "name": "Example Book new updated",
  "authorName": "John lal",
  "publisherName": "Publisher ABC",
  "pages": "600",
  "bookCateogory": "MATH",
  "language": "ENGLISH"
}
  </code>
</pre>

7. DELETE  -  `localhost:9000/book?bid={ book id }`  to delete the book with ID
    <pre>
      <code>
   this is the book that is deleted
    {
    "UID": 2,
    "name": "Example Book New",
    "authorName": "John Doe",
    "publisherName": "Publisher ABC",
    "pages": "600",
    "bookCateogory": "PHYSICS",
    "language": "HINDI",
    "createdOn": 1706961767122,
    "updatedOn": 1706961767122
    }
      </code>
    </pre>

## Lombok & Validation Dependency

### Installation

To use Lombok in your Spring Boot project, add the Lombok dependency to your pom.xml

### Annotations

- `@Getter`: Automatically generates getter methods for all fields in the class.
- `@Setter`: Automatically generates setter methods for all fields in the class.
- `@Builder`: Provides a builder pattern for easier object instantiation.
- `@AllArgsConstructor`: Generates a constructor with arguments for all fields in the class.

## Validation Dependency

## Installation

To enable validation in your Spring Boot project, add the Validation dependency to your `pom.xml`:

### Annotations

- `@NotNull (message = "Name field is required !!")` : the dto parameter cannot be null
- `@NotBlank (message = "Name field is required !!")` : the dto parameter cannot be null + cant be empty
- `@NotEmpty (message = "Name field is required !!")` : the dto parameter cannot be empty
- `@Max(60)` : the max value for the dto parameter can be 60
- `@Min(20)	` : the min value for the dto parameter can be 20
- `@Size(min = 2,max = 20,message = "min 2 and max 20 characters are allowed !!")`:
max and min characters in a String dto parameter

we have to make @Valid in the endpoint parameter | if in the incoming body any of the validation is
not found we get **400 BAD REQUEST**


<pre>
<code>
@PostMapping("/regemployee")
public Employee registerEmployee(@RequestBody @Valid Employeedto request) throws SQLException {
//code 
}
</code>
</pre>

# Properties used
<pre>
<code>
spring.datasource.url=jdbc:mysql://localhost:3306/library?createDatabaseIfNotExist=true
spring.datasource.username = root
spring.datasource.password = admin

spring.jpa.show-sql=true

# none validate  update  create create-drop
spring.jpa.hibernate.ddl-auto=update

logging.level.root=debug

server.PORT = 9000
</code>
</pre>

# JDBC 
learn from doc
