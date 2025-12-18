package com.example.expenseManagerv2.Service;

import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.LoginBean;
import com.example.expenseManagerv2.Bean.LoginRequestBean;
import com.example.expenseManagerv2.dao.AuthDAO;

@Service
public class AuthServiceImpl implements AuthService {
	private AuthDAO authDAO;

	@Override
	public LoginBean login(LoginRequestBean loginRequestBean) {
		return authDAO.login(loginRequestBean.getEmail(), loginRequestBean.getPassword());
	}
	

}
