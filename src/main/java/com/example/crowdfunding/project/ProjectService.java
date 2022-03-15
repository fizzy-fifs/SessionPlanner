package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.business.BusinessRepository;
import com.example.crowdfunding.interfaces.ServiceInterface;
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
    public ResponseEntity<Object> create(Project project) {
        //Save project to db
        Project savedProject = projectRepository.insert(project);

        //Add projects  to related business and save to db
        Business business = savedProject.getProjectOwner();
        business.addToListedProjects(savedProject);
        businessRepository.save(business);

        return ResponseEntity.ok(savedProject);
    }

    @Override
    public ResponseEntity<List<Project>> getAll() {
        List<Project> allProjects = projectRepository.findAll();
        return ResponseEntity.ok(allProjects);
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
