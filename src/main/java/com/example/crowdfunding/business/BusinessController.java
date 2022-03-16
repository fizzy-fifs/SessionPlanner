package com.example.crowdfunding.business;

import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
import com.example.crowdfunding.user.User;
import com.example.crowdfunding.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/businesses")
public class BusinessController extends AbstractController<Business> {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/newbusiness", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> create( @RequestParam(name = "name") @Valid String name,
                                          @RequestParam(name = "description") String description, @RequestParam(name = "userId") String userId,
                                          @RequestParam(name = "images")ArrayList<MultipartFile> images) throws JsonProcessingException {
        //Get associated user
        User user = userRepository.findById(new ObjectId(userId));

        //Upload images and retrieve their corresponding urls
        ArrayList<String> imageUrls = new ArrayList<>();
        for (var eachImage: images) {
            String eachUrl = cloudinaryService.uploadFile(eachImage);
            imageUrls.add(eachUrl);
        }

        // Create and set new business parameters
        Business business = new Business();
        business.setName(name);
        business.setOwner(user);
        business.setDescription(description);
        business.setImages(imageUrls);

        return businessService.create(business);
    }

    @GetMapping
    public ResponseEntity<List<Business>> getAll() throws JsonProcessingException { return businessService.getAll(); }

//    @GetMapping(path = "/getbusinessesbyuserid/{userId}")
//    public ResponseEntity<List<Business>> getBusinessByUserId(@PathVariable("userId") String userId) throws JsonProcessingException {
//        return businessService.getBusinessByUserId(userId);
//    }

    public String update(String id, Business newInfo) {
        return null;
    }

    public String delete(String id) {
        return null;
    }
}
