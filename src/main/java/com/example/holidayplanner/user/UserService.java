package com.example.holidayplanner.user;

import org.bson.types.ObjectId;
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
        if(emailExists(user)){ return "Email already exists"; }

        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.insert(user);
        return "User created successfully";
    }

    public List<User> getUsers() { return userRepository.findAll(); }

    public String updateUser(String userId, User newUserInfo) {

        var userIdToObjectId = new ObjectId(userId);

        User currentUserInfo = userRepository.findById(userIdToObjectId);

        if (currentUserInfo == null) {  return "user with id " + userId + " does not exists"; }

        currentUserInfo.setFirstName(newUserInfo.getFirstName());
        currentUserInfo.setLastName(newUserInfo.getLastName());
        currentUserInfo.setUserName(newUserInfo.getUserName());
        currentUserInfo.setDob(newUserInfo.getDob());
        currentUserInfo.setEmail(newUserInfo.getEmail());
        currentUserInfo.setPassword(newUserInfo.getPassword());

        userRepository.save(currentUserInfo);
        return "User has been successfully updated";
    }

    private boolean emailExists(User user) {
        User x = userRepository.findByEmail(user.getEmail());
        return x == null ? false : true;
    }

}
