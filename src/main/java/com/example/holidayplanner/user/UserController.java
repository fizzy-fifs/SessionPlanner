package com.example.holidayplanner.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping(path = "/newuser")
    public String createNewUser(@RequestBody User user) {  return userService.addNewUser(user); }

    @GetMapping
    public List<User> getAllUsers() { return userService.getUsers(); }
}
