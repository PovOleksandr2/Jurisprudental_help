package com.example.servicecommunications.service;

import com.example.servicecommunications.api.dto.User;
import com.example.servicecommunications.repo.ThreadRepo;
import com.example.servicecommunications.repo.model.Thread;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ThreadService {

    private String identityUrl = "http://service-identities:8081/identities/";
    private final ThreadRepo threadRepo;

    public List<Thread> fetchAll() {
        return threadRepo.findAll();
    }

    public Thread fetchById(long id) throws IllegalArgumentException {
        final Optional<Thread> maybeThread = threadRepo.findById(id);

        if(maybeThread.isEmpty()) throw new IllegalArgumentException("Thread not found");
        else return maybeThread.get();
    }

    private boolean isUserPresent(long expectedUserId) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(expectedUserId);

        final ResponseEntity<User> response = restTemplate
                .exchange(identityUrl + expectedUserId, HttpMethod.GET, userRequest, User.class);
        return response.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public long create(long requesterId, long selectedLawyerId, String history) {
        if (!(isUserPresent(requesterId) && isUserPresent(selectedLawyerId))) throw new IllegalArgumentException("User not found");
        final Thread thread = new Thread(requesterId, selectedLawyerId, history);
        final Thread savedThread = threadRepo.save(thread);

        return savedThread.getId();
    }

    public void update(long id, Long requesterId, Long selectedLawyerId) throws IllegalArgumentException {
        final Optional<Thread> maybeThread = threadRepo.findById(id);
        if(maybeThread.isEmpty()) throw new IllegalArgumentException("Thread not found");

        if (!(isUserPresent(requesterId) && isUserPresent(selectedLawyerId))) throw new IllegalArgumentException("User not found");

        final Thread thread = maybeThread.get();
        thread.setRequesterId(requesterId);
        thread.setSelectedLawyerId(selectedLawyerId);

        threadRepo.save(thread);
    }

    public void addMessage(long id, String message) throws IllegalArgumentException{
        final Optional<Thread> maybeThread = threadRepo.findById(id);
        if(maybeThread.isEmpty()) throw new IllegalArgumentException("Thread not found");

        final Thread thread = maybeThread.get();
        thread.addMessage(message);

        threadRepo.save(thread);
    }

    public void delete(long id) {
        threadRepo.deleteById(id);
    }
}
