package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.ExpenseBean;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Repository
public class ExpenseDAOWrapper implements ExpenseDAO {
	@Autowired
	private Firestore firestore;

	@Override
	public ExpenseBean createExpense(ExpenseBean expenseBean) throws InterruptedException, ExecutionException {
		DocumentReference docRef= firestore.collection("expenses").document();
		ExpenseBean expense= new ExpenseBean();
		expense.setExpenseId(docRef.getId());
		expense.setCreatedAt(Instant.now().toString());
		expense.setGroupId(expenseBean.getGroupId());
		expense.setPaidBy(expenseBean.getPaidBy());
		expense.setAmount(expenseBean.getAmount());
		expense.setSplitBetween(expenseBean.getSplitBetween());
		expense.setDescription(expenseBean.getDescription());
		 
		docRef.set(expense).get();

		return expense;
	}

	@Override
	public List<ExpenseBean> getExpensesByGroupId(String groupId) throws InterruptedException, ExecutionException {
		QuerySnapshot snapShot= firestore.collection("expenses").whereEqualTo("groupId", groupId).get().get();
		List<ExpenseBean> expenses= new ArrayList<>();
		for(DocumentSnapshot doc : snapShot.getDocuments()) {
			expenses.add(doc.toObject(ExpenseBean.class));
		}
		return expenses;
	}

}
