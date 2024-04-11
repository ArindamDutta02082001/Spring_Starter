package com.example.onlinelibrarymanagementsystem.service;

import com.example.onlinelibrarymanagementsystem.dto.CreateBookDto;
import com.example.onlinelibrarymanagementsystem.dto.CreateStudentDto;
import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.Student;
import com.example.onlinelibrarymanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    // creating a new Student Java Object
    private Student createStudent(CreateStudentDto studentDto)
    {
        return  Student.builder()
                .name(studentDto.getName())
                .contact(studentDto.getContact())
                .build();
    }

    public Student saveStudent( CreateStudentDto studentDto)
    {
        Student newStudent = createStudent(studentDto);
        studentRepository.save(newStudent);
        return newStudent;
    }
    public List<Student> getAllStudent()
    {
        return studentRepository.findAll();
    }

    public Student getStudentById( Integer studentId)
    {
        return studentRepository.findById(studentId).orElse(null);
    }

    public Student deleteStudentId( Integer studentId)
    {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student != null)
        {
            studentRepository.deleteById(studentId);
        }
        return student;
    }

}
