package com.example.expenseManagerv2.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.expenseManagerv2.Bean.ExpenseBean;

public interface ExpenseService {
	public ExpenseBean createExpense(ExpenseBean expenseBean) throws InterruptedException, ExecutionException;
	public List<ExpenseBean> getExpensesByGroupId(String groupId) throws InterruptedException, ExecutionException;
}
