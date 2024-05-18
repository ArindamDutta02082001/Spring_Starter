package com.example.project.onlinelibrarywithsecurity.repository;


import com.example.project.onlinelibrarywithsecurity.models.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecuredUserRepository extends JpaRepository<SecuredUser, Integer> {

}
