package com.Student_Course_Microservice.RegistrationService.service;

import com.Student_Course_Microservice.RegistrationService.model.Registration;
import com.Student_Course_Microservice.RegistrationService.repo.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public String registerStudent(Registration request) {

        //steps
        //1-get student info
        //2-get course info
        //3-send notification

        //get student info
        Map studentMap;
        try {
            String studentGetUrl = "http://localhost:8083/students/" + request.getStudentId();
            ResponseEntity<Map> studentResponse = restTemplate.getForEntity(studentGetUrl, Map.class);
            studentMap = studentResponse.getBody();
        } catch (Exception ex) {
            return "student service is unavailable now...!!!";
        }


        //get course info
        Map courseMap;
        try {
            String courseGetUrl = "http://localhost:8082/courses/" + request.getCourseId();
            ResponseEntity<Map> courseResponse = restTemplate.getForEntity(courseGetUrl, Map.class);
            courseMap = courseResponse.getBody();
        } catch (Exception ex) {
            return "Course Service is unavailble now";
        }

        //Save registration details to db
        Registration registration=new Registration();
        registration.setStudentId(request.getStudentId());
        registration.setCourseId(request.getCourseId());
        registrationRepository.save(registration);


        // 3. Send notification
        try {
            String notificationUrl = "http://localhost:8081/notifications";
            Map<String, String> notification = new HashMap<>();
            notification.put("email", (String) studentMap.get("email"));
            notification.put("message", "You have successfully registered for the course: " + courseMap.get("name"));
            ResponseEntity<String> notificationResponse = restTemplate.postForEntity(notificationUrl, notification, String.class);
            return "Registration successful! "
                    +" student "+studentMap.get("name")
                    +" is register to "+courseMap.get("name")+" "
                    + notificationResponse.getBody();

        }catch (Exception ex){
            return "Registration completed but failed to send message";
        }
    }

}
