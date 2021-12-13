package com.help.serviceidentities.service;

import com.help.serviceidentities.repo.UserRepo;
import com.help.serviceidentities.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public User fetchById(long id) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);

        if(maybeUser.isEmpty())
            throw new IllegalArgumentException("User not found");
        else
            return maybeUser.get();
    }

    public long create(String username, String fullName, String contactInfo, String userType) {
        final User user = new User(username, fullName, contactInfo, userType);
        final User savedUser = userRepo.save(user);

        return savedUser.getId();
    }

    public void update(long id, String username, String fullName, String contactInfo, String userType) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);
        if(maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");

        final User user = maybeUser.get();
        if(username != null && !username.isEmpty()) user.setUsername(username);
        if(fullName != null && !fullName.isEmpty()) user.setFullName(fullName);
        if(contactInfo != null && !contactInfo.isEmpty()) user.setContactInfo(contactInfo);
        if(userType != null) user.setUserType(userType);
        userRepo.save(user);
    }

    public void delete(long id) {
        userRepo.deleteById(id);
    }
}
