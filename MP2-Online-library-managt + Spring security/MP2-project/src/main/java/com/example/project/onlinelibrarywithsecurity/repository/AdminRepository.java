package com.example.project.onlinelibrarywithsecurity.repository;

import com.example.project.onlinelibrarywithsecurity.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
