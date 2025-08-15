package com.Student_Course_Microservice.RegistrationService.service;

import com.Student_Course_Microservice.RegistrationService.model.Registration;
import com.Student_Course_Microservice.RegistrationService.repo.RegistrationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // In-memory cache for courses
    private Map<Integer, Map<String, Object>> courseCache = new HashMap<>();

    // Consume course events
    @KafkaListener(topics = "course-topic", groupId = "registration-group")
    public void consumeCourse(String message) {
        try {
            // Use Jackson ObjectMapper to parse JSON

            Map<String, Object> courseMap = objectMapper.readValue(message, Map.class);

            Number idNumber = (Number) courseMap.get("id");
            Integer courseId = idNumber.intValue();
            courseCache.put(courseId, courseMap);

        } catch (Exception ex) {
            System.out.println("Failed to parse course message: " + message);
            ex.printStackTrace();
        }
    }

    // Main registration method
    public String registerStudent(Registration request) {

        // 1. Get student info from StudentService
        Map studentMap;
        try {                        //this is RestTemplate call....
            String studentGetUrl = "http://localhost:8083/students/" + request.getStudentId();
            ResponseEntity<Map> studentResponse = restTemplate.getForEntity(studentGetUrl, Map.class);
            studentMap = studentResponse.getBody();
        } catch (Exception ex) {
            return "Student service is unavailable now!";
        }

        // 2. Get course info from in-memory cache
        Map courseMap = courseCache.get(request.getCourseId());
        if (courseMap == null) {
            return "Course information not available. Try again later!";
        }

        // 3. Save registration to DB
        Registration registration = new Registration();
        registration.setStudentId(request.getStudentId());
        registration.setCourseId(request.getCourseId());
        registrationRepository.save(registration);

        // 4. Produce registration event to Kafka
        String registrationMessage = "{"
                + "\"studentId\": " + request.getStudentId() + ","
                + "\"studentEmail\": \"" + studentMap.get("email") + "\","
                + "\"courseId\": " + request.getCourseId() + ","
                + "\"courseName\": \"" + courseMap.get("name") + "\""
                + "}";
        kafkaTemplate.send("registration-topic", registrationMessage);

        return "Registration successful! Student " + studentMap.get("name")
                + " is registered to " + courseMap.get("name")
                + ". Notification sent via Kafka.";
    }
}
