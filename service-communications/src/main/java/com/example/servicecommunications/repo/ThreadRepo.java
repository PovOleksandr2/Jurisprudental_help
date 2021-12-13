package com.example.servicecommunications.repo;

import com.example.servicecommunications.repo.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepo extends JpaRepository<Thread, Long> {
}
