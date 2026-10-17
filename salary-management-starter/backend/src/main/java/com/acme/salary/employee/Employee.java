package com.acme.salary.employee;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employees", indexes = {
    @Index(name = "idx_employee_name", columnList = "name"),
    @Index(name = "idx_employee_number", columnList = "employee_number")
})
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_number", nullable = false, unique = true, length = 20)
    private String employeeNumber;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 80) private String department;
    @Column(nullable = false, length = 80) private String country;
    @Column(nullable = false, length = 3) private String currency;
    @Column(name = "annual_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal annualSalary;

    protected Employee() {}

    public Employee(String employeeNumber, String name, String department, String country,
                    String currency, BigDecimal annualSalary) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.department = department;
        this.country = country;
        this.currency = currency;
        this.annualSalary = annualSalary;
    }

    public Long getId() { return id; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getCountry() { return country; }
    public String getCurrency() { return currency; }
    public BigDecimal getAnnualSalary() { return annualSalary; }
    public void setAnnualSalary(BigDecimal annualSalary) { this.annualSalary = annualSalary; }
    public void setCurrency(String currency) { this.currency = currency; }
}
