package com.example.expenseManagerv2.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Service.UserService;

@RestController
public class Controller {
	
	private UserService userService;
	public Controller(UserService userService) {
		this.userService=userService;
	}
	@PostMapping("/create")
	public String createUser(@RequestBody UserBean userBean) throws InterruptedException, ExecutionException{
		return userService.createUser(userBean);
	}
	@GetMapping("/get")
	public UserBean getUser(@RequestParam String userId) throws InterruptedException, ExecutionException {
		return userService.getuser(userId);
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<String> testGetEndpoint(){
		return ResponseEntity.ok("test get Endpoint is working");
	}

}
