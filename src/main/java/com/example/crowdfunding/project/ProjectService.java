package com.example.crowdfunding.project;

import com.example.crowdfunding.interfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements ServiceInterface<Project> {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Object> create(Project project) {
        projectRepository.insert(project);
        return ResponseEntity.ok(project);
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
