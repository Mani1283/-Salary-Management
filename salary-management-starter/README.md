# Salary Management Starter

A local assessment starter using Angular, Spring Boot, and SQLite. It supports paged employee search and salary edits, and seeds 10,000 synthetic employees on an empty database.

## Prerequisites

- Java 17+
- Maven 3.9+ (or add the Maven wrapper from your local Spring Initializr project)
- Node.js 20.19+ and npm

## Start the backend

From `backend/` run `mvn spring-boot:run`. The API starts at `http://localhost:8080`; the first start creates `data/salary.db` and seeds it if empty. The API is documented in `docs/API.md`.

## Start the Angular UI

In another terminal, from `frontend/` run `npm install` and then `npm start`. Open `http://localhost:4200`. Angular's development proxy forwards `/api` requests to port 8080.

## Run backend tests

From `backend/` run `mvn test`. Tests use an in-memory SQLite database and do not depend on the 10,000-row local seed.

## What to build next for a submission

Add authentication and role-based authorization before using real salary data. Then add audit history for salary changes, country/currency-aware reporting, database migrations, and deployment configuration. Do not expose this starter on a public network with real employee data as-is.

## Project map

- `docs/requirements.md`: one-page goal, scope, exclusions, and rationale.
- `backend/`: REST API, validation, persistence, and seed data.
- `frontend/`: search, pagination, and inline salary edit UI.
- `docs/API.md`: endpoints and local development notes.

Salary amounts are stored as decimals and currency is stored on each employee. This starter intentionally does not convert currencies or sum unlike currencies.
