package com.Student_Course_Microservice.NotificationService.controller;

import com.Student_Course_Microservice.NotificationService.model.Notification;
import com.Student_Course_Microservice.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> notifyUser(@RequestBody Notification notification){
        String response = notificationService.sendNotification(notification);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
