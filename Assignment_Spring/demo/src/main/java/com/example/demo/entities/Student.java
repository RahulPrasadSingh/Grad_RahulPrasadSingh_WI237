package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private int rollNo;
    private String name;
    private int std;
    private String School;

    public int getRollNo() {
        return rollNo;
    }
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStd() {
        return std;
    }
    public void setStd(int std) {
        this.std = std;
    }
    public String getSchool() {
        return School;
    }
    public void setSchool(String school) {
        School = school;
    }
    
}
