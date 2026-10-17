package com.acme.salary.employee;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

class EmployeeServiceTest {
    private final EmployeeRepository repository = mock(EmployeeRepository.class);
    private final EmployeeService service = new EmployeeService(repository);

    @Test void trimsSearchAndCapsPageSize() {
        when(repository.findByNameContainingIgnoreCaseOrEmployeeNumberContainingIgnoreCase(
            "Maya", "Maya", PageRequest.of(0, 100, Sort.by("name").ascending())))
            .thenReturn(new PageImpl<>(java.util.List.of()));
        service.search(" Maya ", 0, 200);
        verify(repository).findByNameContainingIgnoreCaseOrEmployeeNumberContainingIgnoreCase(
            "Maya", "Maya", PageRequest.of(0, 100, Sort.by("name").ascending()));
    }

    @Test void updatesSalaryAndCurrency() {
        Employee employee = new Employee("AC00001", "Aarav Shah", "Engineering", "India", "INR", new BigDecimal("50000.00"));
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        Employee updated = service.updateSalary(1L, new SalaryUpdateRequest(new BigDecimal("62000.00"), "USD"));
        assertThat(updated.getAnnualSalary()).isEqualByComparingTo("62000.00");
        assertThat(updated.getCurrency()).isEqualTo("USD");
    }

    @Test void returnsNotFoundForUnknownEmployee() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateSalary(9L,
            new SalaryUpdateRequest(new BigDecimal("1000"), "USD")))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("404 NOT_FOUND");
    }
}
