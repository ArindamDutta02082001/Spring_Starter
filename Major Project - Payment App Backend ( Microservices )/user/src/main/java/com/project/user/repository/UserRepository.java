package com.project.user.repository;


import com.project.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByMobile(String mobile); // mobile ~ username
}

