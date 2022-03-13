package com.example.crowdfunding.user;

import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.ControllerInterface;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1.0/users")
public class UserController implements ControllerInterface<User> {

    @Autowired
    private final UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, CloudinaryService cloudinaryService) { this.userService = userService; }

    @Override
    @PostMapping(path = "/newuser")
    public ResponseEntity<Object> create(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(errors.getAllErrors().get(0).getDefaultMessage());
        }

        return userService.create(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody @Valid Map<String, String> emailAndPassword, Errors errors) throws Exception {

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors.getAllErrors());
        }
        return userService.login(emailAndPassword);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> getAll() { return userService.getAll(); }

    @GetMapping(path = "/{userId}")
    public User getUserById(@PathVariable("userId") String userId) throws Exception {
        return userService.getUserById(userId);
    }

    @PostMapping(path = "/uploadprofilepicture")
    public ResponseEntity uploadProfilePicture(@RequestBody MultipartFile image, String userId) {
        ObjectId userIdToObjectId = new ObjectId(userId);
        User user = userRepository.findById(userIdToObjectId);

        if (user == null) { return new ResponseEntity<>("User does not exist", HttpStatus.OK); }
         String imageUrl = cloudinaryService.uploadFile(image);

         user.setImage(imageUrl);
         User updatedUser = userRepository.save(user);

         return new ResponseEntity(updatedUser.getImage(), HttpStatus.OK);
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
