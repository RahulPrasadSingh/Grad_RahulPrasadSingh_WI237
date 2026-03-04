package com.example.docker_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.docker_demo.entities.Student;
import com.example.docker_demo.service.StudentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class StudentController {
    @Autowired
    StudentService service;

    @PostMapping("/student")
    public ResponseEntity<String> putMethodName(@RequestBody Student s) {
        if(service.existById(s.getRegNo())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");
        }
        service.addStudent(s);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student Added: "+s.toString());
    }
}
