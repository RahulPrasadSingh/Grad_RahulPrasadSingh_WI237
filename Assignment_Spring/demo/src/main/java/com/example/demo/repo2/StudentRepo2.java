package com.example.demo.repo2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Student;

public interface StudentRepo2 extends JpaRepository<Student,Integer>{
    
}
