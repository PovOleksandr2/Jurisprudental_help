package com.example.serviceapplications.api;


import com.example.serviceapplications.repo.model.Application;
import com.example.serviceapplications.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public final class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<Application>> index() {
        final List<Application> applications = applicationService.fetchAll();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> show(@PathVariable long id) {
        try{
            final Application application = applicationService.fetchById(id);
            return ResponseEntity.ok(application);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.serviceapplications.api.dto.Application application) {
        final double price = application.getPrice();
        final String problemDescription = application.getProblemDescription();
        final String urgency = application.getUrgency();
        final long requesterID = application.getRequesterId();
        final long selectedLawyerId = application.getSelectedLawyerId();
        final String lawField = application.getLawField();
        final long id = applicationService.create(price, problemDescription, urgency, requesterID, selectedLawyerId, lawField);

        final String location = String.format("/applications/" + id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.serviceapplications.api.dto.Application application) {
        final double price = application.getPrice();
        final String problemDescription = application.getProblemDescription();
        final String urgency = application.getUrgency();
        final long requesterID = application.getRequesterId();
        final long selectedLawyerId = application.getSelectedLawyerId();
        final String lawField = application.getLawField();
        final long threadId = application.getThreadId();

        try{
            applicationService.update(id, price, problemDescription, urgency, requesterID, selectedLawyerId, lawField, threadId);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
