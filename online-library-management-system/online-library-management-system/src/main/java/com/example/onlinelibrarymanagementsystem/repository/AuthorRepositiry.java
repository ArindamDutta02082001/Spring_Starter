package com.example.onlinelibrarymanagementsystem.repository;

import com.example.onlinelibrarymanagementsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositiry extends JpaRepository<Author , Integer> {
    // save , findById , findAllById , deleteById bla bla are implemented
}
