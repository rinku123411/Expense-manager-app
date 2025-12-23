package com.example.expenseManagerv2.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Bean.User;
import com.example.expenseManagerv2.dao.UserDAO;
import com.google.firebase.auth.FirebaseAuthException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO userDAO;
	
	@Override
	public Map<String, Object> createUser(UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException {
		return userDAO.createUser(userBean);
	}

	@Override
	public User getuser(String userId, String email) throws InterruptedException, ExecutionException {
		return userDAO.getUser(userId,email);
	}
	

}
