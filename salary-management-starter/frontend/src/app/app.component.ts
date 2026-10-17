import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Employee, EmployeeApiService, Page } from './employee-api.service';

@Component({
  selector: 'app-root', standalone: true, imports: [CommonModule, FormsModule, CurrencyPipe],
  template: `
    <main class="shell">
      <header class="topbar"><div class="brand-mark">A</div><div><strong>ACME</strong><span>People operations</span></div><span class="env">LOCAL DEMO</span></header>
      <section class="intro"><p class="eyebrow">COMPENSATION · EMPLOYEE DIRECTORY</p><h1>Salary management</h1><p class="lead">Find an employee, review their current pay details, and make a salary update.</p></section>
      <section class="panel">
        <div class="panel-heading"><div><h2>Employees</h2><p>{{ total | number }} records · salaries shown in each employee’s local currency</p></div><label class="search"><span>⌕</span><input [(ngModel)]="query" (ngModelChange)="onQueryChange($event)" placeholder="Search name or employee ID" aria-label="Search employees"></label></div>
        @if (error) { <div class="notice error" role="alert">{{ error }}</div> }
        @if (notice) { <div class="notice success" role="status">{{ notice }}</div> }
        <div class="table-wrap"><table><thead><tr><th>EMPLOYEE</th><th>DEPARTMENT</th><th>COUNTRY</th><th>ANNUAL SALARY</th><th></th></tr></thead><tbody>
          @for (employee of employees; track employee.id) {
            <tr><td><div class="person"><span class="avatar">{{ initials(employee.name) }}</span><span><strong>{{ employee.name }}</strong><small>{{ employee.employeeNumber }}</small></span></div></td><td>{{ employee.department }}</td><td>{{ employee.country }}</td><td>
              @if (editingId === employee.id) { <div class="edit-fields"><input type="number" min="0.01" step="0.01" [(ngModel)]="editSalary" aria-label="Annual salary"><select [(ngModel)]="editCurrency" aria-label="Currency">@for (code of currencies; track code) { <option [value]="code">{{ code }}</option> }</select></div> }
              @else { <strong>{{ employee.annualSalary | currency:employee.currency:'code':'1.2-2' }}</strong> }
            </td><td class="actions">@if (editingId === employee.id) { <button class="save" (click)="save(employee)" [disabled]="saving">Save</button><button class="cancel" (click)="cancelEdit()">Cancel</button> } @else { <button class="edit" (click)="beginEdit(employee)">Edit salary</button> }</td></tr>
          } @empty { <tr><td colspan="5" class="empty">{{ loading ? 'Loading employees…' : 'No employees found. Try another search.' }}</td></tr> }
        </tbody></table></div>
        <footer class="pagination"><span>Showing {{ employees.length ? page * 25 + 1 : 0 }}–{{ page * 25 + employees.length }} of {{ total }}</span><div><button (click)="changePage(page - 1)" [disabled]="page === 0 || loading">Previous</button><span>Page {{ totalPages ? page + 1 : 0 }} of {{ totalPages }}</span><button (click)="changePage(page + 1)" [disabled]="page + 1 >= totalPages || loading">Next</button></div></footer>
      </section>
      <p class="footnote">Demo data is synthetic. Currency amounts are not converted or combined.</p>
    </main>
  `
})
export class AppComponent implements OnInit {
  private readonly api = inject(EmployeeApiService);
  employees: Employee[] = [];
  query = '';
  page = 0;
  total = 0;
  totalPages = 0;
  loading = false;
  saving = false;
  error = '';
  notice = '';
  editingId: number | null = null;
  editSalary = 0;
  editCurrency = 'USD';
  currencies = ['INR', 'USD', 'GBP', 'SGD', 'EUR'];
  private debounce?: ReturnType<typeof setTimeout>;

  ngOnInit(): void { this.load(); }
  onQueryChange(_value: string): void {
    clearTimeout(this.debounce);
    this.debounce = setTimeout(() => { this.page = 0; this.load(); }, 250);
  }
  load(): void {
    this.loading = true; this.error = ''; this.notice = '';
    this.api.search(this.query, this.page).subscribe({
      next: (result: Page<Employee>) => { this.employees = result.content; this.total = result.totalElements; this.totalPages = result.totalPages; this.loading = false; },
      error: () => { this.error = 'Could not load employees. Confirm the Spring Boot API is running on port 8080.'; this.loading = false; }
    });
  }
  changePage(next: number): void { this.page = next; this.load(); }
  beginEdit(employee: Employee): void { this.editingId = employee.id; this.editSalary = employee.annualSalary; this.editCurrency = employee.currency; this.error = ''; this.notice = ''; }
  cancelEdit(): void { this.editingId = null; }
  save(employee: Employee): void {
    if (!Number.isFinite(this.editSalary) || this.editSalary <= 0) { this.error = 'Enter a salary greater than zero.'; return; }
    if (!/^[A-Z]{3}$/.test(this.editCurrency)) { this.error = 'Choose a valid three-letter currency code.'; return; }
    this.saving = true; this.error = '';
    this.api.updateSalary(employee.id, this.editSalary, this.editCurrency).subscribe({
      next: () => { this.saving = false; this.editingId = null; this.notice = `Salary updated for ${employee.name}.`; this.load(); },
      error: (response) => { this.saving = false; this.error = response.error?.detail || response.error?.message || 'Salary update failed. Please try again.'; }
    });
  }
  initials(name: string): string { return name.split(' ').map(part => part[0]).slice(0, 2).join('').toUpperCase(); }
}
