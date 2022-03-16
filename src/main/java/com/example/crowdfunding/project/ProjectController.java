package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
import com.example.crowdfunding.project.enums.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ResponseEntity<Object> create(@RequestParam(name = "title") @Valid String title, @RequestParam(name = "category") String category,
                                         @RequestParam(name = "description") String description, @RequestParam(name = "goal") double goal,
                                         @RequestParam(name = "endDate") String endDate, @RequestParam(name = "businessId") String businessId,
                                         @RequestParam(name = "images")ArrayList<MultipartFile> images) throws JsonProcessingException {

        //Upload images and retrieve their corresponding urls
        ArrayList<String> imageUrls = new ArrayList<>();
        for (var eachImage: images) {
            String eachImageUrl = cloudinaryService.uploadFile(eachImage);
            imageUrls.add(eachImageUrl);
        }
        // Get associated business
        Business business = businessRepository.findById(new ObjectId(businessId));

        //Set category
        try {
            Category.valueOf(category);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid category: " + category);
        }

        //Create new project and set properties
        Project project = new Project();
        project.setTitle(title);
        project.setCategory(Category.valueOf(category));
        project.setDescription(description);
        project.setGoal(BigDecimal.valueOf(goal));
        project.setEndDate(LocalDate.parse(endDate));
        project.setProjectOwner(business);
        project.setImages(imageUrls);

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
