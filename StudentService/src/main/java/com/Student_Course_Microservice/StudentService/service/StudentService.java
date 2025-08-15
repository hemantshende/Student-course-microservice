package com.Student_Course_Microservice.StudentService.service;

import com.Student_Course_Microservice.StudentService.Exceptions.StudentNotFoundException;
import com.Student_Course_Microservice.StudentService.model.Student;
import com.Student_Course_Microservice.StudentService.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(Integer id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }
}
