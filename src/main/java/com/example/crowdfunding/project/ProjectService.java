package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.geocoding.GeocodeLocation;
import com.example.crowdfunding.geocoding.GeocodingService;
import com.example.crowdfunding.interfaces.ServiceInterface;
import com.example.crowdfunding.project.enums.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProjectService implements ServiceInterface<Project> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Object> create(Project project) throws IOException, InterruptedException, ApiException {
        //Get lat and lng for project
        GeocodeLocation latAndLng = geocodingService.getLatAndLng(project);

        //Set project's latitude and longitude
        project.setLatitude(latAndLng.getLatitude());
        project.setLongitude(latAndLng.getLongitude());

        //Save project to db
        Project savedProject = projectRepository.insert(project);

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String projectJson = mapper.writeValueAsString(savedProject);

        return ResponseEntity.ok(projectJson);
    }

    @Override
    public ResponseEntity<Object> getAll() throws JsonProcessingException {
        List<Project> allProjects = projectRepository.findAll();

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String allProjectsJson = mapper.writeValueAsString(allProjects);

        return ResponseEntity.ok(allProjectsJson);
    }

    @Override
    public String update(String entityId, Project newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }

    public ResponseEntity<Object> getProjectById(String projectId) throws JsonProcessingException {
        Project project = projectRepository.findById(new ObjectId(projectId));

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        String projectJson = mapper.writeValueAsString(project);
        return ResponseEntity.ok(projectJson);
    }

    public ResponseEntity<Object> getAllCategories() {
        Category[] allCategories = Category.values();
        return ResponseEntity.ok(allCategories);
    }
}
