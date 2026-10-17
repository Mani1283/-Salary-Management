# Angular UI

Run `npm install` once, then `npm start`. This starts the development server at `http://localhost:4200` and proxies `/api` to `http://localhost:8080`. Start the Spring Boot backend first.

The UI uses Angular standalone components and `HttpClient`. Search is debounced, requests are paged, and salary updates are submitted to the backend API. `EmployeeApiService` is the single place to change the API path or DTO shape.
