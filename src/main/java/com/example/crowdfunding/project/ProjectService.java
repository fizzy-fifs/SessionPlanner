package com.example.crowdfunding.project;

import com.example.crowdfunding.interfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String create(Project project) {
        projectRepository.insert(project);
        return "Project has been successfully created";
    }

    @Override
    public List<Project> getAll() { return projectRepository.findAll(); }

    @Override
    public String update(String entityId, Project newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }
}
