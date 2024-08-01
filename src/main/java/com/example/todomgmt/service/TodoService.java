package com.example.todomgmt.service;

import com.example.todomgmt.model.Todo;
import com.example.todomgmt.repository.ProjectRepository;
import com.example.todomgmt.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Todo createTodo(String projectId, String description) {
        if (projectRepository.existsById(projectId)) {
            Todo todo = new Todo();
            todo.setDescription(description);
            todo.setProjectId(projectId);
            return todoRepository.save(todo);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    public List<Todo> getTodosByProjectId(String projectId) {
        return todoRepository.findByProjectId(projectId);
    }

    public Todo updateTodoStatus(String id, boolean status) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setStatus(status);
            todo.setUpdatedDate(LocalDateTime.now());
            return todoRepository.save(todo);
        } else {
            throw new RuntimeException("Todo not found");
        }
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
