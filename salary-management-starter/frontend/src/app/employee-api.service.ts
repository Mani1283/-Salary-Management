import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee {
  id: number;
  employeeNumber: string;
  name: string;
  department: string;
  country: string;
  currency: string;
  annualSalary: number;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class EmployeeApiService {
  private readonly http = inject(HttpClient);

  search(query: string, page: number): Observable<Page<Employee>> {
    const params = new HttpParams().set('q', query).set('page', page).set('size', 25);
    return this.http.get<Page<Employee>>('/api/employees', { params });
  }

  updateSalary(id: number, annualSalary: number, currency: string): Observable<Employee> {
    return this.http.put<Employee>(`/api/employees/${id}/salary`, { annualSalary, currency });
  }
}
