package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.entities.Student;
import com.example.demo.repo.StudentRepo;
import com.example.demo.repo2.StudentRepo2;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class StudentController {
    @Autowired
    StudentRepo r1;
    @Autowired
    StudentRepo2 r2;

    @RequestMapping("/insert")
    public String xyz(){
        return "index.html";
    }

    @RequestMapping(path="/student/add", method=RequestMethod.POST)
    @ResponseBody
    public String requestMethodName(@RequestBody Student s) {
        if(r1.existsById(s.getRollNo()) || r2.existsById(s.getRollNo())){
            return "Sorry Student already exists!!";
        }
        r1.save(s);
        r2.save(s);
        return "Student added successfully!!";
    }
    
}
