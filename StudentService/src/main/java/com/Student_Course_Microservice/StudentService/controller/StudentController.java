package com.Student_Course_Microservice.StudentService.controller;

import com.Student_Course_Microservice.StudentService.model.Student;
import com.Student_Course_Microservice.StudentService.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student student1=studentService.addStudent(student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer id){
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }
}
