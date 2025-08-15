package com.Student_Course_Microservice.CourseService.service;

import com.Student_Course_Microservice.CourseService.exception.CourseNotFoundException;
import com.Student_Course_Microservice.CourseService.model.Course;
import com.Student_Course_Microservice.CourseService.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course){
        return  courseRepository.save(course);
    }

    public Course getCourseById(Integer id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("course not found"));
    }
}
