package com.example.serviceapplications.repo;

import com.example.serviceapplications.repo.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
}
