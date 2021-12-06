package com.example.holidayplanner.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping(path = "/newuser")
    public String createNewUser(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) { return errors.getAllErrors().get(0).getDefaultMessage(); }
        return userService.addNewUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getUsers(); }
}
