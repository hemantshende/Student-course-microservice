package com.Student_Course_Microservice.CourseService.controller;

import com.Student_Course_Microservice.CourseService.model.Course;
import com.Student_Course_Microservice.CourseService.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course){
        Course savedCourse = courseService.addCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id){
        Course course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
