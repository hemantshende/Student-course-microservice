package com.Student_Course_Microservice.CourseService.repo;

import com.Student_Course_Microservice.CourseService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
}
