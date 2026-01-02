package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.ExpenseBean;
import com.example.expenseManagerv2.Bean.Group;
import com.example.expenseManagerv2.Bean.GroupDashboardBean;
import com.example.expenseManagerv2.Bean.SettlementBean;
import com.example.expenseManagerv2.Service.GroupService;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Repository
public class ExpenseDAOWrapper implements ExpenseDAO {
	@Autowired
	private Firestore firestore;
	
	@Autowired
	private GroupService groupService;

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

	@Override
	public GroupDashboardBean getDashboard(String groupId) throws InterruptedException, ExecutionException {
		
		Group group= groupService.getGroupById(groupId);
		List<ExpenseBean> expenses = getExpensesByGroupId(groupId);
		Map<String, Double> balances= getBalance(expenses);
		Double totalExpense= getExpenseTotal(expenses);
		List<SettlementBean> settlements= getSettlements(balances);
		GroupDashboardBean groupDashboardBean= new GroupDashboardBean();
		groupDashboardBean.setGroupId(group.getGroupId());
		groupDashboardBean.setGroupName(group.getName());
		groupDashboardBean.setMembers(group.getMembersId());
		groupDashboardBean.setTotalExpense(totalExpense);
		groupDashboardBean.setBalances(balances);
		groupDashboardBean.setSettlements(settlements);
		groupDashboardBean.setExpenses(expenses);
		return groupDashboardBean;
	}
	
	public Map<String,Double> getBalance(List<ExpenseBean> expenses ){
		Map<String, Double> balances= new HashMap<>();
		for(ExpenseBean expense: expenses) {
			balances.putIfAbsent(expense.getPaidBy(), 0.0);
			balances.put(expense.getPaidBy(), balances.get(expense.getPaidBy())+expense.getAmount());
			Double amountPerSplit = expense.getAmount()/expense.getSplitBetween().size();
			for(String splitMemberEmail: expense.getSplitBetween()) {
				balances.putIfAbsent(splitMemberEmail, 0.0);
				balances.put(splitMemberEmail, balances.get(splitMemberEmail)- amountPerSplit);
			}
		}
		return balances;
		
	}
	
	public List<SettlementBean> getSettlements(Map<String,Double> balances){
		List<Map.Entry<String, Double>> creditors=new ArrayList<>();
		List<Map.Entry<String, Double>> debitors=new ArrayList<>();
		for(Map.Entry<String,Double> entry: balances.entrySet()) {
			if(entry.getValue()>0) creditors.add(entry);
			else if(entry.getValue()<0) debitors.add(entry);
		}
		int i=0, j=0;
		List<SettlementBean> settlements= new ArrayList<>();
		while(i<debitors.size() && j<creditors.size()) {
			Map.Entry<String, Double> debtorEntry = debitors.get(i);
	        Map.Entry<String, Double> creditorEntry = creditors.get(j);
			String debtor= debtorEntry.getKey();
			double debt= -debtorEntry.getValue();
			
			String creditor= creditorEntry.getKey();
			double credit= creditorEntry.getValue();
			
			double settleAmount= Math.min(credit, debt);
			settlements.add(new SettlementBean(debtor,creditor,settleAmount));
			debtorEntry.setValue(debitors.get(i).getValue()+settleAmount);
			creditorEntry.setValue(creditors.get(i).getValue()-settleAmount);
			if (Math.abs(debtorEntry.getValue()) < 0.0001) i++;
	        if (Math.abs(creditorEntry.getValue()) < 0.0001) j++;
		}
		return settlements;
		
	}
	
	public double getExpenseTotal(List<ExpenseBean> expenses) {
		double total=0.0;
		for(ExpenseBean expense: expenses) {
			total+=expense.getAmount();
		}
		return total;
	}

}
