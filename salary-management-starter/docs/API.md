# API and local development

Base URL: `http://localhost:8080/api`

## `GET /employees?q=&page=0&size=25`

Searches case-insensitively in employee name and employee number. Returns Spring's paged JSON response with `content`, `totalElements`, `totalPages`, `number`, and `size`. Page size is capped at 100.

## `PUT /employees/{id}/salary`

Body: `{"annualSalary": 85000.00, "currency": "USD"}`. Salary must be greater than zero; currency must contain three uppercase letters. Returns the updated employee, `400` for invalid input, or `404` when the employee does not exist.

## Local database

SQLite is stored at `backend/data/salary.db`. Remove that file to reset the local demo and trigger a fresh seed. The seed runs only when the employee table is empty.

## Design notes

Search and paging happen in the database so the browser only receives one page at a time. Salary values use `BigDecimal`/decimal storage to avoid floating-point rounding in Java. The UI formats display values for readability but submits decimal numeric values. Add authentication, authorization, and audit logging before using real data.
