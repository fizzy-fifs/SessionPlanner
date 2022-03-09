package com.example.crowdfunding.project;

import com.example.crowdfunding.interfaces.ControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/projects")
public class ProjectController implements ControllerInterface<Project>{

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) { this.projectService = projectService; }

    @Override
    @PostMapping(path = "/newproject")
    public String create(@RequestBody @Valid Project project, Errors errors) {

        if (errors.hasErrors()) { return errors.getAllErrors().get(0).getDefaultMessage(); }
        return projectService.create(project);
    }

    @Override
    @GetMapping
    public List<Project> getAll() { return projectService.getAll(); }

    @Override
    public String update(String id, Project newInfo) {
        return null;
    }

    @Override
    public String delete(String id) {
        return null;
    }
}
