package com.example.docker_demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private int regNo;
    private int rollNo;
    private String name;
    private int std;
    public int getRegNo() {
        return regNo;
    }
    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }
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
    @Override
    public String toString() {
        return "Student [regNo=" + regNo + ", rollNo=" + rollNo + ", name=" + name + ", std=" + std + "]";
    }
}
