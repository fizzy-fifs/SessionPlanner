package com.example.crowdfunding.project;

import com.example.crowdfunding.address.Address;
import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.example.crowdfunding.interfaces.AbstractController;
import com.example.crowdfunding.project.enums.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.errors.ApiException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    public ResponseEntity create(@RequestParam(name = "title") @Valid String title, @RequestParam(name = "category") String category,
                                         @RequestParam(name = "description") String description, @RequestParam(name = "goal") double goal,
                                         @RequestParam(name = "endDate") LocalDate endDate, @RequestParam(name = "businessId") String businessId,
                                         @ModelAttribute Address address, @RequestParam(name = "images")ArrayList<MultipartFile> images, Errors errors) throws IOException, InterruptedException, ApiException {

        if (errors.hasErrors()) { return ResponseEntity.ok(errors.getAllErrors().get(0).getDefaultMessage()); }

        String escapedCategory = category.replaceAll("\\s+","");
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
            Category.valueOf(escapedCategory);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid category: " + category);
        }

        //Create new project and set properties
        Project project = new Project();
        project.setTitle(title);
        project.setCategory(Category.valueOf(escapedCategory));
        project.setDescription(description);
        project.setGoal(goal);
        project.setEndDate(endDate);
        project.setProjectOwner(business);
        project.setAddress(address);
        project.setImages(imageUrls);

        return projectService.create(project);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() throws JsonProcessingException { return projectService.getAll(); }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjectById(@PathVariable("projectId") String projectId) throws JsonProcessingException {
        return projectService.getProjectById(projectId);
    }

    @GetMapping("/getcategories")
    public ResponseEntity<Object> getAllCategories(){
        return projectService.getAllCategories();
    }

    public String update(String id, Project newInfo) {
        return null;
    }

    public String delete(String id) {
        return null;
    }
}
