package com.acme.salary.employee;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
    private final EmployeeRepository employees;
    public EmployeeService(EmployeeRepository employees) { this.employees = employees; }

    public Page<Employee> search(String query, int page, int size) {
        if (page < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page must be zero or greater");
        int safeSize = Math.min(Math.max(size, 1), 100);
        String term = query == null ? "" : query.trim();
        return employees.findByNameContainingIgnoreCaseOrEmployeeNumberContainingIgnoreCase(
            term, term, PageRequest.of(page, safeSize, Sort.by("name").ascending()));
    }

    @Transactional
    public Employee updateSalary(long id, SalaryUpdateRequest request) {
        Employee employee = employees.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        employee.setAnnualSalary(request.annualSalary());
        employee.setCurrency(request.currency());
        return employee;
    }
}
