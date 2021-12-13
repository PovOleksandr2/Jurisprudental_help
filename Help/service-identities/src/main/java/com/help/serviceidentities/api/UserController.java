package com.help.serviceidentities.api;

import com.help.serviceidentities.repo.model.User;
import com.help.serviceidentities.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/identities")
public final class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try{
            User user = userService.fetchById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<com.help.serviceidentities.api.dto.User> getDtoById(@PathVariable long id) {
        try {
            System.out.println(id);
            User user = userService.fetchById(id);
            System.out.println(2);
            com.help.serviceidentities.api.dto.User userDto = new com.help.serviceidentities.api.dto.User(user.getUsername(), user.getFullName(),
                    user.getContactInfo(), user.getUserType());
            System.out.println(3);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            System.out.println(4);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.help.serviceidentities.api.dto.User user) {
        final String username = user.username();
        final String fullName = user.fullName();
        final String contactInfo = user.contactInfo();
        final String userType = user.userType();
        final long id = userService.create(username, fullName, contactInfo, userType);

        final String location = String.format("/identities/" + id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.help.serviceidentities.api.dto.User user) {
        final String username = user.username();
        final String fullName = user.fullName();
        final String contactInfo = user.contactInfo();
        final String userType = user.userType();

        try{
            userService.update(id, username, fullName, contactInfo, userType);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
