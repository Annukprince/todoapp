package com.example.todomgmt.service;

import com.example.todomgmt.model.Project;
import com.example.todomgmt.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(String title) {
        Project project = new Project();
        project.setTitle(title);
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public Project updateProject(String id, String title) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setTitle(title);
            project.setCreatedDate(LocalDateTime.now());
            return projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
