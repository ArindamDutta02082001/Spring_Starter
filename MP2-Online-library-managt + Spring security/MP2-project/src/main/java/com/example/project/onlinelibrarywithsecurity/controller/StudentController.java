package com.example.project.onlinelibrarywithsecurity.controller;


import com.example.project.onlinelibrarywithsecurity.dto.CreateStudentDto;
import com.example.project.onlinelibrarywithsecurity.models.Student;
import com.example.project.onlinelibrarywithsecurity.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    // student
    // create new Student
    // get a student by id
    // delete a student by id

    @Autowired
    StudentService studentService;
    @PostMapping("/regstudent")
    public Student createAndSaveStudent(@RequestBody @Valid CreateStudentDto createStudentDto)
    {
        return studentService.saveStudent(createStudentDto);
    }

    @GetMapping("/allstudent")
    public List<Student> getAllStudent()
    {
        return studentService.getAllStudent();
    }

    @GetMapping("/student/{stuid}")
    public Student getStudentById(@PathVariable("stuid") Integer studentId)
    {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/delstudent")
    public String deleteStudentById(@RequestParam("sid") Integer studentId)
    {
                studentService.deleteStudentId(studentId);
        return "student deleted";
    }

    @PutMapping("/student")
    public Student updateStudentById( @RequestParam("sid") Integer studentId)
    {
        // todo
        return null;
    }



}
