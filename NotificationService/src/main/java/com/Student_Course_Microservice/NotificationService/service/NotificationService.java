package com.Student_Course_Microservice.NotificationService.service;

import com.Student_Course_Microservice.NotificationService.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendNotification(Notification notification){
        System.out.println("Sending notification to: " + notification.getEmail());
        System.out.println("Message: " + notification.getMessage());
        return "Notification sent to " + notification.getEmail();
    }

    // Kafka listener to consume messages from CourseService
    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeNotification(String message) {
        try {
            // Parse JSON message into Notification object
            Notification notification = objectMapper.readValue(message, Notification.class);
            sendNotification(notification);
        } catch (Exception ex) {
            System.out.println("Failed to process notification: " + message);
            ex.printStackTrace();
        }
    }
}
