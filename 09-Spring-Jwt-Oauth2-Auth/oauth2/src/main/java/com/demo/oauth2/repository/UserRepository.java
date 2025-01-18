package com.demo.oauth2.repository;

import com.demo.oauth2.models.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<DemoUser,Integer> {

    @Query("SELECT u FROM DemoUser u WHERE u.username = :username")
    DemoUser findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("update DemoUser u Set u.password = :password where u.username = :username")
    public void changePassword(String password , String username);
}
