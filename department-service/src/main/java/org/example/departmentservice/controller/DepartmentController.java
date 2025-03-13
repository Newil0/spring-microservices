package org.example.departmentservice.controller;

import jakarta.validation.Valid;
import org.example.departmentservice.model.Department;
import org.example.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public Department add(@RequestBody @Valid Department department) {
        LOGGER.info("department add {}", department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        LOGGER.info("department findById {}", id);
        return departmentRepository.findById(id);
    }

    @GetMapping
    public List<Department> findAll() {
        LOGGER.info("department findAll");
        return departmentRepository.findAll();
    }

}
