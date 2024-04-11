package com.example.onlinelibrarymanagementsystem.repository;

import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookRepository extends JpaRepository<Book , Integer> {


    // save , findById , findAllById , deleteById bla bla are implemented

    @Modifying
    @Transactional
    @Query("update Book b set b.name = :name , b.genre = :genre , b.pages = :pages  where id = :bookId ")
    public void updateBook(String name , Genre genre , Integer pages ,  Integer bookId);

    // search a book by individual functions
    List<Book> findByName(String searchValue);
    List<Book> findByGenre(Genre searchValue);

    @Query()




}
