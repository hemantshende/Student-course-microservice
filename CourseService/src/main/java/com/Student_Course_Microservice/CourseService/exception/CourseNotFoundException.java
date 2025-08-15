package com.Student_Course_Microservice.CourseService.exception;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String message){
        super(message);
    }
}
