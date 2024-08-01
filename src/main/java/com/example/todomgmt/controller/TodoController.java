package com.example.todomgmt.controller;

import com.example.todomgmt.model.Todo;
import com.example.todomgmt.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/{projectId}")
    public Todo createTodo(@PathVariable String projectId, @RequestBody CreateTodoRequest request) {
        return todoService.createTodo(projectId, request.getDescription());
    }

    @GetMapping("/{projectId}")
    public List<Todo> getTodosByProjectId(@PathVariable String projectId) {
        return todoService.getTodosByProjectId(projectId);
    }

    @PutMapping("/{id}")
    public Todo updateTodoStatus(@PathVariable String id, @RequestBody UpdateTodoStatusRequest request) {
        return todoService.updateTodoStatus(id, request.isStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    static class CreateTodoRequest {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    static class UpdateTodoStatusRequest {
        private boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
