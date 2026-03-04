package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Emp;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    Emp e;
    public void raiseSalary(){
          int salary= e.getSalary();
          e.setSalary(salary+900);
    }
}
