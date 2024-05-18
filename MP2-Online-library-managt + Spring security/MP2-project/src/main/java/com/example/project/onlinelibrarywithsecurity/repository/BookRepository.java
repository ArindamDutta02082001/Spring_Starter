package com.example.project.onlinelibrarywithsecurity.repository;


import com.example.project.onlinelibrarywithsecurity.models.Book;
import com.example.project.onlinelibrarywithsecurity.models.Student;
import com.example.project.onlinelibrarywithsecurity.models.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {


    // save , findById , findAllById , deleteById bla bla are implemented


    // update a book if we don`t use .save()
    @Modifying
    @Transactional
    @Query("update Book b set b.name = :name , b.genre = :genre , b.pages = :pages , b.authored_by.id = :authorId  where id = :bookId ")
    public void updateBook(String name , Genre genre , Integer pages , Integer bookId , Integer authorId);
        // check the dot notation in JPQL



    // search a book by individual functions
    List<Book> findByName(String searchValue);

    List<Book> findByGenre(Genre searchValue);



    // assigning the book to a student
    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.my_student = ?2 where b.id = ?1 and b.my_student is null")
    void assignBookToStudent(int bookId, Student student);


    // unassigning the book from the student
    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.my_student = null where b.id = ?1 ")
    void unassignBookToStudent(int bookId);




}
