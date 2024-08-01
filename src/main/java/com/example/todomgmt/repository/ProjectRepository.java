package com.example.todomgmt.repository;

import com.example.todomgmt.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
