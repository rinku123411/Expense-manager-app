package com.example.expenseManagerv2.Bean;

import java.util.List;

public class Group {
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getMembersId() {
		return membersId;
	}
	public void setMembersId(List<String> membersId) {
		this.membersId = membersId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	private List<String> membersId;
	private String createdAt;
	private String createdBy;
	private String groupId;

}
