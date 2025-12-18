package com.example.expenseManagerv2.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Bean.User;
import com.example.expenseManagerv2.Service.UserService;
import com.example.expenseManagerv2.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuthException;

@RestController
public class Controller {
	
	private UserService userService;
	public Controller(UserService userService) {
		this.userService=userService;
	}
	@PostMapping("/create")
	public String createUser(@RequestBody UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException{
		return userService.createUser(userBean);
	}
	@GetMapping("/get")
	public User getUser(@RequestParam(required= false) String userId, @RequestParam(required= false) String email) throws InterruptedException, ExecutionException {
		return userService.getuser(userId, email);
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<String> testGetEndpoint(){
		return ResponseEntity.ok("test get Endpoint is working");
	}

}
