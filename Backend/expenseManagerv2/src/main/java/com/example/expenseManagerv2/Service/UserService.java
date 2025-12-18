package com.example.expenseManagerv2.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Bean.User;
import com.google.firebase.auth.FirebaseAuthException;


@Service
public interface UserService {
String createUser(UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException;
User getuser(String userId, String email) throws InterruptedException, ExecutionException;

}
