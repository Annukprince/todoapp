package com.example.todomgmt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "Welcome to the ToDo Management Application!";
    }
}
