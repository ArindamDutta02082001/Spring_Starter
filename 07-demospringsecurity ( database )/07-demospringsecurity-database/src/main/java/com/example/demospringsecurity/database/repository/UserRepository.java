package com.example.demospringsecurity.database.repository;

import com.example.demospringsecurity.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :username")
    public User findByUsername(String username) ;
}
