package com.example.crowdfunding.user;

import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1.0/users")
public class UserController extends AbstractController<User> {

    @Autowired
    private final UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, CloudinaryService cloudinaryService) { this.userService = userService; }


    @PostMapping(path = "/newuser")
    public ResponseEntity create(@RequestBody @Valid User user, Errors errors) throws JsonProcessingException {

        if (errors.hasErrors()) {
            return ResponseEntity.ok(errors.getAllErrors().get(0).getDefaultMessage());
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


    @GetMapping
    public ResponseEntity<Object> getAll() { return userService.getAll(); }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable("userId") String userId) throws Exception {
        return userService.getUserById(userId);
    }

    @PostMapping(path = "/uploadprofilepicture")
    public ResponseEntity uploadProfilePicture(@RequestPart MultipartFile image, @RequestParam String userId) {
        ObjectId userIdToObjectId = new ObjectId(userId);
        User user = userRepository.findById(userIdToObjectId);

        if (user == null) { return new ResponseEntity<>("User does not exist", HttpStatus.OK); }
         String imageUrl = cloudinaryService.uploadFile(image);

         user.setImage(imageUrl);
         User updatedUser = userRepository.save(user);

         return new ResponseEntity(updatedUser.getImage(), HttpStatus.OK);
    }


    @PutMapping (path = "/{userId}")
    public String update(@PathVariable("userId") String userId, @RequestBody User newUserInfo) {
        return userService.update(userId, newUserInfo);
    }


    @DeleteMapping(path = "/{userId}")
    public String delete(@PathVariable("userId") String userId) {
        return userService.delete(userId);
    }

}
