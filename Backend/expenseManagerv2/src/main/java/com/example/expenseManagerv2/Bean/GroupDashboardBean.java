package com.example.expenseManagerv2.Bean;

import java.util.List;
import java.util.Map;

public class GroupDashboardBean {
	
	private String groupId;
    private String groupName;
    private List<String> members;
    private double totalExpense;
    private Map<String, Double> balances;
    private List<SettlementBean> settlements;
	private List<ExpenseBean> expenses;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public double getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}
	public Map<String, Double> getBalances() {
		return balances;
	}
	public void setBalances(Map<String, Double> balances) {
		this.balances = balances;
	}
	public List<ExpenseBean> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<ExpenseBean> expenses) {
		this.expenses = expenses;
	}
	public List<SettlementBean> getSettlements() {
		return settlements;
	}
	public void setSettlements(List<SettlementBean> settlements) {
		this.settlements = settlements;
	}

}
