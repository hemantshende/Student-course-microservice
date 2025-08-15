package com.Student_Course_Microservice.StudentService.Exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message){
        super(message);
    }
}
