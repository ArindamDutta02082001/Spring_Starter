package com.example.project.onlinelibrarywithsecurity.service.libraryService;


import com.example.project.onlinelibrarywithsecurity.cacheRepository.StudentCacheRepository;
import com.example.project.onlinelibrarywithsecurity.dto.CreateStudentDto;
import com.example.project.onlinelibrarywithsecurity.models.securitymodels.SecuredUser;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Student;
import com.example.project.onlinelibrarywithsecurity.repository.libraryRepository.StudentRepository;
import com.example.project.onlinelibrarywithsecurity.responseObject.studentResponse;
import com.example.project.onlinelibrarywithsecurity.service.securityService.SecuredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    SecuredUserService securedUserService;

    @Autowired
    StudentRepository studentRepository;

    // implementing cache
    @Autowired
    StudentCacheRepository studentCacheRepository;


//     creating a new Student Object from the student DTO using builder
//    private Student createStudent(CreateStudentDto studentDto)
//    {
//        return  Student.builder()
//                .name(studentDto.getName())
//                .contact(studentDto.getContact())
//                .build();
//    }
/* the above method of creating the new Student will change as we have to create an secured user
    that can be either user , admin */

// before creating a student , SecuredUser should have a new row
public Student createStudent(CreateStudentDto createStudentDto){

    SecuredUser securedUser = SecuredUser.builder()
            .authorities("student")
            .username(createStudentDto.getUsername())
            .password(new BCryptPasswordEncoder().encode(createStudentDto.getPassword()))
            .isEnabled(true)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();

    // create a secured user
    securedUserService.saveUserinDB(securedUser);

    // create a student
    Student student = Student.builder()
            .name(createStudentDto.getName())
            .contact(createStudentDto.getContact())
            .securedUser(securedUser)
            .build();

    return studentRepository.save(student);
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

    public studentResponse getStudentById(Integer studentId)
    {
        // implementing the redis-cache
        long start = System.currentTimeMillis();
        studentResponse cacheStudent =  studentCacheRepository.getValue(studentId);
        if ( cacheStudent == null )
        {
            Student student1 = studentRepository.findById(studentId).orElse(null);
            studentResponse studentResponse = new studentResponse(student1);
            long end = System.currentTimeMillis();
            System.out.println(end-start);

            studentCacheRepository.setValue(studentId , studentResponse );
            return studentResponse;

        }
        else
        {
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        }
            return cacheStudent;

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
