import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Expense } from 'src/app/models/group-dashboard.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ExpenseApiService {
  private baseUrl = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}
  addExpense(expense: Expense) {
    return this.http.post<Expense>(`${this.baseUrl}/add-expense`, expense);
  }
}
