package com.Student_Course_Microservice.CourseService.service;

import com.Student_Course_Microservice.CourseService.exception.CourseNotFoundException;
import com.Student_Course_Microservice.CourseService.model.Course;
import com.Student_Course_Microservice.CourseService.repo.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    // Simple in-memory cache (optional)
    private Map<Integer, Integer> lastRegisteredStudent = new HashMap<>();


    // --- CRUD operations ---
    public Course addCourse(Course course) {
        Course savedCourse = courseRepository.save(course);

        try {
            // Convert course object to JSON
            String courseMessage = objectMapper.writeValueAsString(savedCourse);
            // Send message to Kafka
            kafkaTemplate.send("course-topic", courseMessage);
            System.out.println("Sent course message to Kafka: " + courseMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedCourse;
    }


    public Course getCourseById(Integer id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("course not found"));
    }

    // --- Kafka listener ---
    @KafkaListener(topics = "registration-topic", groupId = "course-group")
    public void handleRegistration(String message) {
        try {
            // Parse registration event
            Map<String, Object> event = objectMapper.readValue(message, Map.class);

            Integer courseId = (Integer) event.get("courseId");
            Integer studentId = (Integer) event.get("studentId");
            lastRegisteredStudent.put(courseId, studentId);

            System.out.println("CourseService processed registration: " + event);

            // Send notification message
            String notificationMessage = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("notification-topic", notificationMessage);

        } catch (Exception ex) {
            System.out.println("Error processing registration message: " + message);
            ex.printStackTrace();
        }
    }
}
