package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.interfaces.ServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements ServiceInterface<Project> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Object> create(Project project) throws JsonProcessingException {
        //Save project to db
        Project savedProject = projectRepository.insert(project);

        //Add projects  to related business and save to db
        Business business = savedProject.getProjectOwner();
//        business.addToListedProjects(savedProject);
//        businessRepository.save(business);

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
}
