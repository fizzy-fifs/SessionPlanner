package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/projects")
public class ProjectController extends AbstractController<Project> {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    public ProjectController(ProjectService projectService) { this.projectService = projectService; }


    @PostMapping(path = "/newproject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> create(@RequestPart @Valid Project project, Errors errors,
                         @RequestPart(name = "businessId") String businessId, @RequestPart ArrayList<MultipartFile> images ) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(errors.getAllErrors().get(0).getDefaultMessage());
        }

        //Upload images and retrieve their corresponding urls
        ArrayList<String> imageUrls = new ArrayList<>();
        for (var eachImage: images) {
            String eachImageUrl = cloudinaryService.uploadFile(eachImage);
            imageUrls.add(eachImageUrl);
        }

        project.setImages(imageUrls);

        ObjectId businessIdToObjectID = new ObjectId(businessId);
        Business business = businessRepository.findById(businessIdToObjectID);

        project.setProjectOwner(business);
        return projectService.create(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() { return projectService.getAll(); }

    public String update(String id, Project newInfo) {
        return null;
    }

    public String delete(String id) {
        return null;
    }
}
