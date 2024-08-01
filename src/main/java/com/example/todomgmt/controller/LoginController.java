package com.example.todomgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns the name of the login HTML template
    }
}
