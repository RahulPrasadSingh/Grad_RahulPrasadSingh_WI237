package com.example.demo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.example.demo.service.EmpService;

public class AssigTest {
     EmpService e;
    @Test
    public void interfaceEmpServiceTest(){
          e=mock(EmpService.class);
          e.raiseSalary();
          e.raiseSalary();
          verify(e, times(2)).raiseSalary();
          
    }
}
