package com.example.expenseManagerv2.Service;

import com.example.expenseManagerv2.Bean.LoginBean;
import com.example.expenseManagerv2.Bean.LoginRequestBean;

public interface AuthService {
	public LoginBean login(LoginRequestBean loginRequestBean);

}
