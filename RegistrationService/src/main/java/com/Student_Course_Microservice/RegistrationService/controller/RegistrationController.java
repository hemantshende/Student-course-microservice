package com.Student_Course_Microservice.RegistrationService.controller;

import com.Student_Course_Microservice.RegistrationService.model.Registration;
import com.Student_Course_Microservice.RegistrationService.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Registration request){
        String response = registrationService.registerStudent(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
