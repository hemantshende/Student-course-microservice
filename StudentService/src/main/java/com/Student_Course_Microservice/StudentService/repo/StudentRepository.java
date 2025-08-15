package com.Student_Course_Microservice.StudentService.repo;

import com.Student_Course_Microservice.StudentService.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
