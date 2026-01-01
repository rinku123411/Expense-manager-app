package com.example.expenseManagerv2.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;

import com.example.expenseManagerv2.Bean.ExpenseBean;
import com.example.expenseManagerv2.Bean.GroupDashboardBean;

public interface ExpenseDAO {
	public ExpenseBean createExpense(ExpenseBean expenseBean) throws InterruptedException, ExecutionException;
	public List<ExpenseBean> getExpensesByGroupId(String groupId) throws InterruptedException, ExecutionException;
	public GroupDashboardBean getDashboard( String groupId) throws InterruptedException, ExecutionException;
}
