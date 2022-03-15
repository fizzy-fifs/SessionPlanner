package com.example.crowdfunding.business;

import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
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

    @PostMapping(path = "/newbusiness", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> create(@RequestBody @Valid Business business, @RequestPart ArrayList<MultipartFile> images, Errors errors)  {

        if (errors.hasErrors()) {  return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); }

        //Upload images and retrieve their corresponding urls
        ArrayList<String> imageUrls = new ArrayList<>();
        for (var eachImage: images) {
            String eachUrl = cloudinaryService.uploadFile(eachImage);
            imageUrls.add(eachUrl);
        }
        business.setImages(imageUrls);

        return businessService.create(business);
    }

    public ResponseEntity<List<Business>> getAll() {
        return businessService.getAll();
    }

    public String update(String id, Business newInfo) {
        return null;
    }

    public String delete(String id) {
        return null;
    }
}
