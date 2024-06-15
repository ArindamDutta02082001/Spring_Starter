package com.demo.oauth2.repository;

import com.demo.oauth2.models.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DemoUser,Integer> {

    @Query("SELECT u FROM DemoUser u WHERE u.username = :username")
    DemoUser findByUsername(@Param("username") String username);
}
