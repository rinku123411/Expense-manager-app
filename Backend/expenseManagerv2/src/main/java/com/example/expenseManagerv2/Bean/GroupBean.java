package com.example.expenseManagerv2.Bean;

import java.util.List;

public class GroupBean {
	private String groupName;
	private List<String> membersEmail;
	private String createdBy;
	public List<String> getMembersEmail() {
		return membersEmail;
	}
	public void setMembersEmail(List<String> membersEmail) {
		this.membersEmail = membersEmail;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getName() {
		return groupName;
	}
	public void setName(String groupName) {
		this.groupName = groupName;
	}
	

}
