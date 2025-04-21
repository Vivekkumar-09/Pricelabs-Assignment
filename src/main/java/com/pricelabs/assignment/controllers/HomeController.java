package com.pricelabs.assignment.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    // for testing the application and its functions
    @GetMapping()
    public String greet(){
        return "Hello, World";
    }
}
