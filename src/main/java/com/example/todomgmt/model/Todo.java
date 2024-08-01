package com.example.todomgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;
    private String description;
    private boolean status = false;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();
    private String projectId;
}
