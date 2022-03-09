package com.example.crowdfunding.user;

import com.example.crowdfunding.interfaces.ControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/users")
public class UserController implements ControllerInterface<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @Override
    @PostMapping(path = "/newuser")
    public String create(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) { return errors.getAllErrors().get(0).getDefaultMessage(); }
        return userService.create(user);
    }

    @Override
    @GetMapping
    public List<User> getAll() { return userService.getAll(); }

    @GetMapping(path = "/{userId}")
    public User getUserById(@PathVariable("userId") String userId) throws Exception {
        return userService.getUserById(userId);
    }

    @Override
    @PutMapping (path = "/{userId}")
    public String update(@PathVariable("userId") String userId, @RequestBody User newUserInfo) {
        return userService.update(userId, newUserInfo);
    }

    @Override
    @DeleteMapping(path = "/{userId}")
    public String delete(@PathVariable("userId") String userId) {
        return userService.delete(userId);
    }

}
