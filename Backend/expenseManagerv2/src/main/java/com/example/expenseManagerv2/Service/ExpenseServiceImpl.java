package com.example.expenseManagerv2.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.ExpenseBean;
import com.example.expenseManagerv2.dao.ExpenseDAO;
@Service
public class ExpenseServiceImpl implements ExpenseService{
	@Autowired
	private ExpenseDAO expenseDAO;

	@Override
	public ExpenseBean createExpense(ExpenseBean expenseBean) throws InterruptedException, ExecutionException {
		return expenseDAO.createExpense(expenseBean);
	}

	@Override
	public List<ExpenseBean> getExpensesByGroupId(String groupId) throws InterruptedException, ExecutionException {
		return expenseDAO.getExpensesByGroupId(groupId);
	}

}
