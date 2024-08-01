package com.example.todomgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String title;
    private LocalDateTime createdDate = LocalDateTime.now();
    private List<Todo> todos = new ArrayList<>();
}
