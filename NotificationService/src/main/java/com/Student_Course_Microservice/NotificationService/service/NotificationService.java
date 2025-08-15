package com.Student_Course_Microservice.NotificationService.service;

import com.Student_Course_Microservice.NotificationService.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public String sendNotification(Notification notification){
        // For now, just log the message
        System.out.println("Sending notification to: " + notification.getEmail());
        System.out.println("Message: " + notification.getMessage());

        return "Notification sent to " + notification.getEmail();
    }
}
