package org.example.employeeservice.controller;

import org.example.employeeservice.model.Employee;
import org.example.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        return employeeRepository.add(employee);
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
