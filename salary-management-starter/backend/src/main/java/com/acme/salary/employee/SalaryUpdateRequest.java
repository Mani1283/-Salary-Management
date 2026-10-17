package com.acme.salary.employee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record SalaryUpdateRequest(
    @NotNull @DecimalMin(value = "0.01", message = "Salary must be greater than zero")
    BigDecimal annualSalary,
    @NotBlank @Pattern(regexp = "[A-Z]{3}", message = "Currency must be a three-letter uppercase code")
    String currency
) {}
