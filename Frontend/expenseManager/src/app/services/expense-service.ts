import { Injectable } from '@angular/core';
import { ExpenseApiService } from '../core/api/expense-api.service';
import { Expense } from '../models/group-dashboard.model';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  constructor(private expenseAPIService: ExpenseApiService) {}
  addExpense(expense: Expense) {
    return this.expenseAPIService.addExpense(expense);
  }
}
