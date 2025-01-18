package com.example.project.onlinelibrarywithsecurity.repository.libraryRepository;


import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // save , findById , findAllById , deleteById bla bla are implemented


}
