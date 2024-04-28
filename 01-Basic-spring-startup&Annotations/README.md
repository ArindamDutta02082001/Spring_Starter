
# Online Library Management
It is a online library management system , where
<br/>
<br/>
## Tables present
Author , Book , Student , Transaction

## Database tables
1. Author Table  <br/>
   ![img.png](images/img.png)   <br/>
   ![img_5.png](images/img_5.png)
2. Book Table  <br/>
   ![img_1.png](images/img_1.png) <br/>
   ![img_6.png](images/img_6.png)
4. Student Table  <br/>
   ![img_2.png](images/img_2.png)<br/>
   ![img_8.png](images/img_8.png)
5. Transaction table  <br/>
   ![img_3.png](images/img_3.png)<br/>
   ![img_7.png](images/img_7.png)

## Important Relations among tables
1. A Author can write many Books & A Book can be written by one author [ Author : Book :: 1 : N ]
2. A Student can borrow many books & A Book can be borrowed by one Student ( Here it is assumed only one copy of a book exist in library else same name ka boht books store ho rha tha and findBookByName mei list return ho ra tha, so one book can be taken by only one student ) [ Student : Book :: 1 : M ]
3. A Book can have many transactions associated to it [ Book : Transaction :: 1 : M ]
4. A Admin is free to do any no of issue and return Transaction for a student [ student : transacio :: 1 : M ]

## Database Schema

![img_4.png](images/img_4.png)


## App functionality
- Admin can create a Book with its details like name , id , author details
- Admin can create 2 type of txn for a student <br/>
    * Issue Txn
        *       1. To start the issue txn we take the book name and student id
                2. Then we are checking whether book is available or not or that student exists or if the book is occupied
                    ( If not then the book and student has to be created from the other endpoints )
                3. If validation success ! then create a txn with pending status
                4. Assign the book to that particular student i.e update book table and set student_id = student passed in the url [ student that tries to book the book ]
                5. Update the txn accordingly with status as SUCCESS or FAILED if any exception occured

    * Return Txn
        *       1. To start the return txn we take the book name and student id
                2. Then we are checking whether book is available or not or that student exists or if that book is genuinely taken by that stuent
                3. If validation success ! then create a txn with pending status
                4. Un-assign the book to that particular student i.e update book table and set student_id = null to deallocate
                5. Update the txn accordingly with status as SUCCESS or FAILED if any exception occured

## APIs
1. Book
* Create a new Book - `localhost:9000/book/regbook`
* Get a book by id - `localhost:9000/book/book/1`
* Get all books - `localhost:9000/book/allbook`
* Search a book by "name", "author_name", "genre", "pages", "id" - `localhost:9000/book/search`
 <pre><code>
payload :
{
    "searchKey" : "genre",
    "searchValue" : "FICTION",
    "operator" : "="
}
</code></pre>
* Update a book by id - `localhost:9000/book/updatebook?bid=1 ( cannot update as it is a foreign key table )`
* Delete a book by id - `localhost:9000/book/delbook?bid=2 ( cannot delete as it is a foreign key table )`
2. Author
* Create a new author - `localhost:9000/author/regauthor`
* Get all author - `localhost:9000/author/allauthor`
3. Student
* Creating a new student - `localhost:9000/student/regstudent`
* Get a student by id - `localhost:9000/student/student/1`
* Get all student - `localhost:9000/student/allstudent`
* Delete a student by id - `localhost:9000/student/delstudent?sid=1`
4. Transaction
* Get all transactions - `localhost:9000/transaction/`
* Start a issue transaction - `localhost:9000/transaction/issue?name=Booknew2&studentId=1`
* Start a return transaction - `localhost:9000/transaction/return?name=Booknew2&studentId=1`

## Payloads and response of apis
 <pre>
 <code>
 payload and response :

 New Book 
 payload :
 {
    "name": "Booknew1",
    "genre" : "FICTION",
    "pages" : 5225,
    "author" : 
    {
    "email" : "arindamdutta@mail.com",
    "authorName" : "Arindam Kr. Dutta" ,
    "country" : "INDIA"
    }
}

response :
{
    "id": 1,
    "name": "Booknew1",
    "genre": "FICTION",
    "pages": 5225,
    "createdOn": "2024-04-20T10:42:35.981+00:00",
    "updatedOn": "2024-04-20T10:42:35.981+00:00",
    "authored_by": {
        "id": 1,
        "email": "arindamdutta@mail.com",
        "authorName": "Arindam Kr. Dutta",
        "country": "INDIA",
        "addedOn": "2024-04-20T10:42:35.962+00:00"
    },
    "my_student": null,
    "transactionList_B": null
}

Update Book
payload :
{
	"name": "Booknew1",
    "genre" : "FICTION",
    "pages" : 50505,
    "author" : 
    {
    "email" : "arindamkrdutta@mail.com",
    "authorName" : "Arindam Kumar. Dutta" ,
    "country" : "INDIA"
    }

}
response :
{
    "id": 1,
    "name": "Booknew1",
    "genre": "FICTION",
    "pages": 5225,
    "createdOn": "2024-04-20T10:59:19.049+00:00",
    "updatedOn": "2024-04-20T10:59:21.242+00:00",
    "authored_by": {
        "id": 2,
        "email": "arindamkrdutta@mail.com",
        "authorName": "Arindam Kumar. Dutta",
        "country": "INDIA",
        "addedOn": "2024-04-20T10:59:21.213+00:00"
    },
    "my_student": null,
    "transactionList_B": []
}

New Student
payload :

{
    "name" :"Author Akamai",
    "contact": "8962340776"
}

response :
{
    "id": 1,
    "name": "Author Akamai",
    "contact": "8962340776",
    "createdOn": "2024-04-20T10:56:04.457+00:00",
    "updatedOn": "2024-04-20T10:56:04.457+00:00",
    "bookList_S": null,
    "transactionList_S": null
}

New Author 
payload :
{
   "authorName" : "Arindam",
    "email" : "arindam@mail.com",
    "country" : "INDIA" 
}

response :
{
    "id": 1,
    "email": "arindam@mail.com",
    "authorName": "Arindam",
    "country": "INDIA",
    "addedOn": "2024-04-20T05:50:00.685+00:00",
    "bookList": null
}

Payloads of issue & return txn is not needed

 </code>
 </pre>

## Properties used
<pre>
<code>
spring.datasource.url=jdbc:mysql://localhost:3306/online-library?createDatabaseIfNotExist=true
         #online-library-management db is created inside tables are there
spring.datasource.username= root
spring.datasource.password = admin

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create

server.PORT = 9000
</code>
</pre>