package com.example.docker_demo.service;

import com.example.docker_demo.entities.Student;

public interface StudentService {
    void addStudent(Student s);
    boolean existById(int id);
}
