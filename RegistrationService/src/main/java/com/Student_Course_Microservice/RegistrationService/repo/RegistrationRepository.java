package com.Student_Course_Microservice.RegistrationService.repo;

import com.Student_Course_Microservice.RegistrationService.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Integer> {
}
