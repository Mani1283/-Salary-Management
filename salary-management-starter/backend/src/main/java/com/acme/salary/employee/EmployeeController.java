package com.acme.salary.employee;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }

    @GetMapping
    public Page<Employee> search(@RequestParam(defaultValue = "") String q,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "25") int size) {
        return service.search(q, page, size);
    }

    @PutMapping("/{id}/salary")
    public Employee updateSalary(@PathVariable long id, @Valid @RequestBody SalaryUpdateRequest request) {
        return service.updateSalary(id, request);
    }
}
