package com.example.expenseManagerv2.Bean;

import java.util.List;

public class ExpenseBean {
	private String expenseId;
    private String groupId;
    private String paidBy;
    private double amount;
    private List<String> splitBetween;
    private String description;
    private String createdAt;
	public String getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public List<String> getSplitBetween() {
		return splitBetween;
	}
	public void setSplitBetween(List<String> splitBetween) {
		this.splitBetween = splitBetween;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
