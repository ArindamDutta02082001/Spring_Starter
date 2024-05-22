package com.example.project.onlinelibrarywithsecurity.repository;


import com.example.project.onlinelibrarywithsecurity.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // save , findById , findAllById , deleteById bla bla are implemented


}
