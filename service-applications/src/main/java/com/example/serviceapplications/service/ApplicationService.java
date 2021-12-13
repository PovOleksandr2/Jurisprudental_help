package com.example.serviceapplications.service;

import com.example.serviceapplications.api.dto.Thread;
import com.example.serviceapplications.api.dto.User;
import com.example.serviceapplications.repo.ApplicationRepo;
import com.example.serviceapplications.repo.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private String communicationUrl = "http://service-communications:8080/communications/";
    private String identityUrl = "http://service-identities:8081/identities/dto/";
    private final ApplicationRepo applicationRepo;

    public List<Application> fetchAll() {
        return applicationRepo.findAll();
    }

    public Application fetchById(long id) throws IllegalArgumentException {
        final Optional<Application> maybeApplication = applicationRepo.findById(id);

        if(maybeApplication.isEmpty()) throw new IllegalArgumentException("Application not found");
        else return maybeApplication.get();
    }

    private boolean isUserPresent(long expectedUserId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(expectedUserId);
        System.out.println(expectedUserId);
        final ResponseEntity<User> response = restTemplate
                .exchange(identityUrl + expectedUserId, HttpMethod.GET, userRequest, User.class);
        return response.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    private long createThread(long requesterId, long selectedLawyerID) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Thread> userRequest = new HttpEntity<>(new Thread(0, requesterId, selectedLawyerID, ""));
        System.out.println(userRequest);
        final ResponseEntity<Thread> response = restTemplate
                .postForEntity(communicationUrl, userRequest, Thread.class );
        return response.getBody().id();
    }

    public long create(double price, String problemDescription, String urgency, long requesterId, long selectedLawyerId, String lawField) throws IllegalArgumentException {
        if (!(isUserPresent(requesterId) && isUserPresent(selectedLawyerId))) throw new IllegalArgumentException("User not found");
        long threadId = createThread(requesterId, selectedLawyerId);
        final Application application = new Application(price, problemDescription, urgency, requesterId, selectedLawyerId, lawField, threadId);
        final Application savedApplication = applicationRepo.save(application);

        return savedApplication.getId();
    }

    public void update(long id, Double price, String problemDescription, String urgency, Long requesterId, Long selectedLawyerId, String lawField, Long threadId) throws IllegalArgumentException {
        final Optional<Application> maybeApplication = applicationRepo.findById(id);
        if(maybeApplication.isEmpty()) throw new IllegalArgumentException("Application not found");
        if(!(isUserPresent(requesterId) && isUserPresent(selectedLawyerId))) throw new IllegalArgumentException("User not found");

        final Application application = maybeApplication.get();
        if(price != null) application.setPrice(price);
        if(problemDescription != null && !problemDescription.isEmpty()) application.setProblemDescription(problemDescription);
        if(urgency != null && !urgency.isEmpty()) application.setUrgency(urgency);
        application.setRequesterId(requesterId);
        application.setSelectedLawyerId(selectedLawyerId);
        if(threadId != null) application.setThreadId(threadId);
        if(lawField != null) application.setLawField(lawField);
        applicationRepo.save(application);
    }

    public void delete(long id) {
        applicationRepo.deleteById(id);
    }
}
