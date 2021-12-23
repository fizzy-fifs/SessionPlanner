package com.example.holidayplanner.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String addNewUser(User user) {
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.insert(user);
        return "User created successfully";
    }

    public List<User> getUsers() { return userRepository.findAll(); }

    public String updateUser(String userId, User newUserInfo) {
        User currentUserInfo = userRepository.findById(userId);

        if (currentUserInfo == null) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }

        currentUserInfo.setFirstName(newUserInfo.getFirstName());
        currentUserInfo.setLastName(newUserInfo.getLastName());
        currentUserInfo.setUserName(newUserInfo.getUserName());
        currentUserInfo.setDob(newUserInfo.getDob());
        currentUserInfo.setEmail(newUserInfo.getEmail());
        currentUserInfo.setPassword(newUserInfo.getPassword());

        userRepository.save(currentUserInfo);
        return "User has been successfully updated";
    }
}
