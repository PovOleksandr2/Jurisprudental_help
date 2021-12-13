package com.example.servicecommunications.api;

import com.example.servicecommunications.api.dto.Message;
import com.example.servicecommunications.api.dto.Thread;
import com.example.servicecommunications.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/communications")
public final class ThreadController {

    private final ThreadService threadService;

    @GetMapping
    public ResponseEntity<List<com.example.servicecommunications.repo.model.Thread>> index() {
        final List<com.example.servicecommunications.repo.model.Thread> threads = threadService.fetchAll();
        return ResponseEntity.ok(threads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.servicecommunications.repo.model.Thread> show(@PathVariable long id) {
        try{
            final com.example.servicecommunications.repo.model.Thread thread = threadService.fetchById(id);
            return ResponseEntity.ok(thread);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Thread> create(@RequestBody Thread thread) {
        final long requesterId = thread.requesterId();
        final long selectedLawyerId = thread.selectedLawyerId();
        final String history = thread.history();
        final long id = threadService.create(requesterId, selectedLawyerId, history);

        Thread threadResponseDto = new Thread(id, requesterId, selectedLawyerId, history);
        return ResponseEntity.ok(threadResponseDto);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<Void> addMessage(@PathVariable long id, @RequestBody Message messageDto) {
        final String message = messageDto.text();
        try{
            System.out.println(message);
            threadService.addMessage(id, message);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Thread thread) {
        final long requesterId = thread.requesterId();
        final long selectedLawyerId = thread.selectedLawyerId();

        try{
            threadService.update(id, requesterId, selectedLawyerId);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        threadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
