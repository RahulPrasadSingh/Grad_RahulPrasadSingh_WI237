package com.example.docker_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.docker_demo.entities.Student;
import com.example.docker_demo.repo.StudentRepo;

@Service
public class StudentServieImpl implements StudentService {
    @Autowired
    StudentRepo repo;

    @Override
    public void addStudent(Student s) {
        repo.save(s);
    }

    @Override
    public boolean existById(int id) {
        if(repo.existsById(id)){
            return true;
        }
        return false;
    }
} 
