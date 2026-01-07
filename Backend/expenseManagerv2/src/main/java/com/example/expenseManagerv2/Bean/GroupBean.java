package com.example.expenseManagerv2.Bean;

import java.util.List;

public class GroupBean {
	private String name;
	private List<String> membersId;
	private String createdBy;
	
	public List<String> getMembersId() {
		return membersId;
	}
	public void setMembersId(List<String> membersId) {
		this.membersId = membersId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String groupName) {
		this.name = groupName;
	}
	

}
