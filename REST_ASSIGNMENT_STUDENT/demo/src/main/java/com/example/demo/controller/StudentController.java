package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Student;
import com.example.demo.repo.StudentRepo;

@RestController
public class StudentController {

    @Autowired
    StudentRepo repo;

    @GetMapping("/students")
    public List<Student> getAll(){
         return repo.findAll();
    }

    @GetMapping("/students/{regNo}")
    public Optional<Student> getById(@PathVariable int regNo){
        return repo.findById(regNo);
    }

    @PostMapping("/students")
    public String addStudent(@RequestBody Student s){
        if(repo.existsById(s.getRegNo())){
            System.out.println("Incoming regNo = " + s.getRegNo());
            return "Student already exists...";
        }
        if(repo.existsByRollNoAndStdAndSchool(s.getRollNo(),s.getStd(), s.getSchool())){
            return "Student with given rollno exists in the given School...";
        }
        repo.save(s);
        return "Student added";
    }

    @PutMapping("/students/{regNo}")
    public String updateStudent(@PathVariable int regNo,@RequestBody Student s){
        if(!repo.existsById(regNo)){
            return "No such student exists..";
        }
        repo.save(s);
        return "Student data updated";
    }
    
    @PatchMapping("/students/{regNo}")
     public ResponseEntity<String> updateStudentPartial(@PathVariable int regNo,@RequestBody Student updatedStudent) {

      Optional<Student> optionalStudent = repo.findById(regNo);

      if(optionalStudent.isEmpty()){
        return ResponseEntity.ok("Student not found");
      }  
      Student existing = optionalStudent.get();
      if(updatedStudent.getName() != null){
        existing.setName(updatedStudent.getName());
      }
      if(updatedStudent.getSchool() != null){
        existing.setSchool(updatedStudent.getSchool());
      }
     if(updatedStudent.getGender() != null){
        existing.setGender(updatedStudent.getGender());
     }
     if(updatedStudent.getStd() != 0){
        existing.setStd(updatedStudent.getStd());
     }
     if(updatedStudent.getRollNo() != 0){
        existing.setRollNo(updatedStudent.getRollNo());
     }
     if(updatedStudent.getPercentage() != 0){
        existing.setPercentage(updatedStudent.getPercentage());
     }
     repo.save(existing);
     return ResponseEntity.ok("Student updated successfully");
    }

    @DeleteMapping("/students/{regNo}")
    public String deleteStudent(@PathVariable int regNo){
        if(!repo.existsById(regNo)){
            return "No such student exists..";
        }
        repo.deleteById(regNo);;
        return "Student data deleted";
    }

    @GetMapping("/students/school")
    public List<Student> getBySchoolName(@RequestParam String name){
        return repo.findBySchool(name);
    }
    
    @GetMapping("/students/school/count")
    public int getBySchoolCount(@RequestParam String name){
        return repo.countBySchool(name);
    }

    @GetMapping("/students/school/standard/count")
    public int getBySchoolCount(@RequestParam int std){
        return repo.countByStd(std);
    }
    
    @GetMapping("/students/result")
    public List<Student> getStudentsByPassOrFail(@RequestParam boolean result){
        return repo.findByPassOrFail(result);
    }

    @GetMapping("/students/strength")
    public int countByGenderAndStd(@RequestParam String gender,@RequestParam int std){
        return repo.countByGenderAndStd(gender,std);
    }

}
