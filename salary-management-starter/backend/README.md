# Spring Boot API

Run `mvn spring-boot:run` from this directory. The first run creates the SQLite file under `data/` and seeds 10,000 synthetic records if the employee table is empty. `mvn test` runs focused service tests.

The repository handles database access, service layer applies search and page bounds, controller defines REST routes, and request record validates salary updates. `EmployeeSeed` uses fixed lists and arithmetic so the same synthetic rows are generated every time the database is reset.
