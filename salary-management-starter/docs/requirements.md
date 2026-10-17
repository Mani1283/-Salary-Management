# Salary Management Requirements

## Goal

Give ACME's HR manager a dependable web application for finding employees, reviewing their current annual salary, and making a salary correction without managing multiple spreadsheets. The first release should demonstrate the workflow against 10,000 synthetic employee records.

## Users and core workflow

The primary user is an authorized HR manager. They search by employee name or employee number, browse a paginated list, inspect department, country, currency, and annual salary, then edit and save an employee's salary. The application confirms a successful save and reports validation or server errors clearly.

## Scope and features

- Angular web interface and Spring Boot REST API backed by SQLite for local assessment use.
- Seed 10,000 deterministic, synthetic employees when the database is empty; never overwrite existing data on startup.
- Server-side search and pagination; cap requested page size to protect the API.
- Update an employee's annual salary and ISO-style three-letter currency code with input validation.
- Preserve country and currency alongside every salary. Do not aggregate different currencies into a misleading total.
- Automated tests for salary validation, employee-not-found behavior, and the core update/search path.
- Local run instructions and API notes accompany the code.

## Deliberate exclusions

- Payroll calculation, tax, benefits, bonuses, and payment execution are excluded because the prompt asks to manage and answer questions about salary data, not run payroll.
- Currency conversion and cross-currency totals are excluded because reliable rates, dates, and reporting rules are not specified.
- Authentication, authorization, approval workflows, and audit history are not implemented in this local demonstration. They are mandatory before any real salary data or production deployment; a real system must control access and retain a change history.
- Bulk import/export, multi-tenant support, and production deployment are excluded from the initial vertical slice to keep the core workflow testable and focused.

## Acceptance checks

HR can launch both apps locally, search 10,000 seeded synthetic records without loading them all into the browser, change a salary, and see the saved value. Invalid salary/currency inputs are rejected. Restarting the API does not create duplicate seed employees.
