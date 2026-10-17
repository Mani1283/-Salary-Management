package com.acme.salary.employee;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSeed implements CommandLineRunner {
    private static final List<String> FIRST = List.of("Aarav", "Maya", "Noah", "Amara", "Liam", "Iris", "Arjun", "Sofia", "Ethan", "Zara");
    private static final List<String> LAST = List.of("Shah", "Patel", "Reed", "Kim", "Garcia", "Singh", "Brown", "Chen", "Khan", "Lopez");
    private static final List<String> DEPARTMENTS = List.of("Engineering", "Finance", "People", "Sales", "Operations");
    private static final List<String> COUNTRIES = List.of("India", "United States", "United Kingdom", "Singapore", "Germany");
    private static final List<String> CURRENCIES = List.of("INR", "USD", "GBP", "SGD", "EUR");
    private final EmployeeRepository employees;
    public EmployeeSeed(EmployeeRepository employees) { this.employees = employees; }

    @Override public void run(String... args) {
        if (employees.count() != 0) return;
        for (int start = 0; start < 10_000; start += 500) {
            int end = Math.min(start + 500, 10_000);
            var batch = new java.util.ArrayList<Employee>(end - start);
            for (int i = start; i < end; i++) {
                int country = i % COUNTRIES.size();
                int salaryUnits = 3_000 + ((i * 37) % 12_000);
                batch.add(new Employee(String.format("AC%05d", i + 1),
                    FIRST.get(i % FIRST.size()) + " " + LAST.get((i / 7) % LAST.size()),
                    DEPARTMENTS.get(i % DEPARTMENTS.size()), COUNTRIES.get(country), CURRENCIES.get(country),
                    BigDecimal.valueOf(salaryUnits * 100L).setScale(2)));
            }
            employees.saveAll(batch);
            employees.flush();
        }
    }
}
