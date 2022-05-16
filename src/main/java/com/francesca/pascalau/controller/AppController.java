package com.francesca.pascalau.controller;

import com.francesca.pascalau.data.entities.Employee;
import com.francesca.pascalau.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class AppController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();

        return new ResponseEntity<>(employees, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        employeeService.add(employee);

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
