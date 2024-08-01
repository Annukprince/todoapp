package com.example.todomgmt.repository;

import com.example.todomgmt.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByProjectId(String projectId);
}
