package com.example.expenseManagerv2.Bean;

import java.util.List;

public class GroupBean {
	private String groupName;
	private List<String> membersId;
	private String createdBy;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<String> getMembersId() {
		return membersId;
	}
	public void setMembersId(List<String> membersId) {
		this.membersId = membersId;
	}
	public String getName() {
		return groupName;
	}
	public void setName(String name) {
		this.groupName = name;
	}
	

}
