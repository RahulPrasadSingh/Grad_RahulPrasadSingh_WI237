package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Student;

public interface StudentRepo extends JpaRepository<Student,Integer>
{
    public boolean existsByRollNoAndStdAndSchool(int rollNo,int std,String school);
    public List<Student>  findBySchool(String name);
    public int countBySchool(String school);
    public int countByStd(int std);
    @Query("""
       SELECT s FROM Student s
       WHERE (:pass = true AND s.percentage > 40)
          OR (:pass = false AND s.percentage <= 40)
       ORDER BY s.percentage Desc
       """)
    public List<Student> findByPassOrFail(boolean pass);
    public int countByGenderAndStd(String gender,int std);
}
