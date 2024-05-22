package com.example.project.onlinelibrarywithsecurity.repository;


import com.example.project.onlinelibrarywithsecurity.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepositiry extends JpaRepository<Author, Integer> {
    // save , findById , findAllById , deleteById bla bla are implemented

    @Query("select a from Author a where a.email = :email ")
    public  Author findAuthorByEmailfromDB(String email);


}
