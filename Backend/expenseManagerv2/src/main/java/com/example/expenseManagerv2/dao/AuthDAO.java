package com.example.expenseManagerv2.dao;

import com.example.expenseManagerv2.Bean.LoginBean;

public interface AuthDAO {
	public LoginBean login(String email, String password);

}
