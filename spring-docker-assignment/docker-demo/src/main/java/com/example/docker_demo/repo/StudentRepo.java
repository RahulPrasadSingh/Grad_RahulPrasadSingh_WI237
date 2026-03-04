package com.example.docker_demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.docker_demo.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    
}
