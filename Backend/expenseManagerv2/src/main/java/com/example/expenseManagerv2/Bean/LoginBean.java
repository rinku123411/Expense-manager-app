package com.example.expenseManagerv2.Bean;

public class LoginBean {
	private String idToken;
	private String refreshToken;
	private String userId;
	public LoginBean(String idToken, String refreshToken, String userId) {
		super();
		this.idToken = idToken;
		this.refreshToken = refreshToken;
		this.userId = userId;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
