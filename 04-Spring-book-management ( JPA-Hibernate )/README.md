# This is a demo of springboot + hibernate JPA
We follow the MVC model
>FLOW  → controller  → service → repository → model

1. **controller** → in this folder we will have the api endpoints , and service logic function are called here
   when endpoints are hit<br/>
   @RestController → the http endpoints are written under this function | it tells that the function
   will contain http rest endpoints
2. **dto** → helps in conversion from JSON data from **UI ←→ Java Object**  | This contains the request model to accept the http request JSON data from the UI <br/>
3. **service / DAO / BO** → the logic of backend after getting data in backend from endpoint | triggered when endpoint is hit <br/>
   @Service → it tells that the function will contain the service logic of the rest endpoints
4. **entity/model** → helps in conversion from **Java Object ←→ Database Schema / Tables** | It is a data models or objects i.e attribute definition for the database or schema <br/>
5. **repository** → creating data object & saving them to  database or in memory logic <br/>
   @Repository → it tells that the function will contain the logic of the DB layer<br/><br/>


### ORM with JPA & Hibernate
**Object-Relational Mapping (ORM):** Utilizing frameworks like Hibernate to map Java objects to relational database tables.
<br/>
<br/>
**Step 1:** Dependency Setup
Include necessary dependencies +  spring-data-jpa dependency.
<br/>
<br/>
**Step 2:** as soon as you install JDBC and SQL dependencies , instantly provide the following in the application.properties file
JDBC properties i.e url , username , pwd + extra ddl line + optional lines else you get error
<br/>
<br/>
**Step 3:** Define Entities and Columns to create table
Use annotations like @Entity, @Id, @GeneratedValue, @Enumerated, @CreationTimestamp, @UpdateTimestamp, and @Column to define tables and columns in Java objects.
<br/>
<br/>
**Step 4:** Create Repository Interface
Create a repository interface by extending JpaRepository<Entity, Id_type>.
Implement custom queries using @Query annotation.
Optionally, use @Modifying and @Transactional for DML queries and data consistency.
<br/>
<br/>
**Step 5:** Establishing Relationships

we have 4 annotation to map between one table to another that fall in PF-FK relation
-  @ManyToOne
- @OneToMany ( used to back link )
- @ManyToMany
- @OneToOne
  > How to figure out when to use what @Annotation
  format : @CurrentTableToOtherTable

**@JoinColumn**
- This annotation makes an extra column for the PK of the parent table which acts as a
  FK in the current table
- it makes the attribute to act as a foreign key for another table and creates a column in current table
  of the primary key of the other table
- jispe @JoinColumn rehta hai that is the child table
- It is must that the data in parent table has to be entered first then data in the child table

**@JsonIgnoreProperties**
- it is used to prevent recursion that occurs due to backlinking .
- Mention all the backlinking variables as shown below
  e.g
<pre>
  @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,
  "students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
  @ManyToOne
  @JoinColumn
  private Student my_student;
</pre>

### Different relationships
- **@ManytoOne**
  jisme @ManytoMany use hua hai usme entity type variable use hoga and that table will be the child
  table with the PK .
  `@ManyToOne private Author authored_by;`
- **@OnetoMany**
  jisme @OnetoMany use hua hai usme List<entity> type variable use hoga and that table will be the
  parent table with the PK .
  `@OneToMany(mappedBy = "books_in_trans_table")
  private List<Transaction> transactionList_B ;`
- **@ManyToMany**
  `@ManyToMany(mappedBy = "books_in_trans_table")
  private List<Transaction> transactionList_B ;`
- **@OneToOne**
  `@OneToOne private Author authored_by;`

### Custom SQL Queries
- Use **@Query** & **@Modifying** annotation to execute custom SQL queries.
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


### Api here
1. GET - `localhost:9000/book`  to get all the books  <br/>
2. GET - `localhost:9000/book/{ book id }`  to get the details of a book by id <br/>
3. GET - `localhost:9001/getbookbylang?lang=HINDI`  to get the details of a book by it language ( HINDI , ENGLISH , BENGALI) <br/>
4. GET - `localhost:9000/getbookbycateo?cateo=PHYSICS`  to get the details of a book by it cateogory <br/>
5. POST - `localhost:9000/book`  to create a new book

<pre>
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
</pre>

6. PUT -  `localhost:9000/book?bid={ book id }` to update a book by its id

<pre>
payload :
{
  "name": "Example Book new updated",
  "authorName": "John lal",
  "publisherName": "Publisher ABC",
  "pages": "600",
  "bookCateogory": "MATH",
  "language": "ENGLISH"
}
</pre>

7. DELETE  -  `localhost:9000/book?bid={ book id }`  to delete the book with ID
    <pre>
      
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
      
    </pre>

### Lombok & Validation Dependency

#### Lombok Dependency Installation

To use Lombok in your Spring Boot project, add the Lombok dependency to your pom.xml & make sure to install and enable the Intellij Plugin for lombok (else IDE wont detect annotations)
   File -> Setting -> Plugin -> install

#### Annotations

- `@Getter`: Automatically generates getter methods for all fields in the class.
- `@Setter`: Automatically generates setter methods for all fields in the class.
- `@Builder`: Provides a builder pattern for easier object instantiation.
- `@AllArgsConstructor`: Generates a constructor with arguments for all fields in the class.

#### Validation Dependency Installation

To enable validation in your Spring Boot project, add the Validation dependency to your `pom.xml`:

#### Annotations

- `@NotNull (message = "Name field is required !!")` : the dto parameter cannot be null
- `@NotBlank (message = "Name field is required !!")` : the dto parameter cannot be null + cant be empty
- `@NotEmpty (message = "Name field is required !!")` : the dto parameter cannot be empty
- `@Max(60)` : the max value for the dto parameter can be 60
- `@Min(20) ` : the min value for the dto parameter can be 20
- `@Size(min = 2,max = 20,message = "min 2 and max 20 characters are allowed !!")`:
  max and min characters in a String dto parameter

<pre>

we have to make @Valid in the endpoint parameter | if in the incoming body any of the validation is
not found we get **400 BAD REQUEST**

@PostMapping("/regemployee")
public Employee registerEmployee(@RequestBody @Valid Employeedto request) throws SQLException {
//code 
}

</pre>

### application.properties file
<pre>
spring.datasource.url=jdbc:mysql://localhost:3306/library?createDatabaseIfNotExist=true
spring.datasource.username = root
spring.datasource.password = admin

spring.jpa.show-sql=true

# none validate  update  create create-drop
spring.jpa.hibernate.ddl-auto=update

logging.level.root=debug

server.PORT = 9000
</pre>


