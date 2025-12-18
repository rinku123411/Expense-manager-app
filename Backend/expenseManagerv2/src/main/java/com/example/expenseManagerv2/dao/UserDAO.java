package com.example.expenseManagerv2.dao;

import java.util.concurrent.ExecutionException;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Bean.User;
import com.google.firebase.auth.FirebaseAuthException;

public interface UserDAO {
	String createUser(UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException;
	User getUser(String userId, String email) throws InterruptedException, ExecutionException;

}
