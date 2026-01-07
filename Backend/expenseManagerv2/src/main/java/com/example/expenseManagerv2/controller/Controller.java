package com.example.expenseManagerv2.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Bean.ExpenseBean;
import com.example.expenseManagerv2.Bean.Group;
import com.example.expenseManagerv2.Bean.GroupBean;
import com.example.expenseManagerv2.Bean.GroupDashboardBean;
import com.example.expenseManagerv2.Bean.LoginBean;
import com.example.expenseManagerv2.Bean.LoginRequestBean;
import com.example.expenseManagerv2.Bean.User;
import com.example.expenseManagerv2.Service.AuthService;
import com.example.expenseManagerv2.Service.ExpenseService;
import com.example.expenseManagerv2.Service.GroupService;
import com.example.expenseManagerv2.Service.UserService;
import com.google.firebase.auth.FirebaseAuthException;

@CrossOrigin(origins = "http://localhost:8100")
@RestController
public class Controller {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping("/create-user")
	public Map<String, Object> createUser(@RequestBody UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException{
		return userService.createUser(userBean);
	}
	@GetMapping("/get-user")
	public User getUser(@RequestParam(required= false) String userId, @RequestParam(required= false) String email) throws InterruptedException, ExecutionException {
		return userService.getuser(userId, email);
	}
	@GetMapping("/get-user-by-email")
	public User getUserByEmail(@RequestParam String email) throws InterruptedException, ExecutionException {
		return userService.getuserByEmail(email);
	}
	
	@RequestMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestBean loginRequestBean) {
		LoginBean login= authService.login(loginRequestBean);
		 return ResponseEntity.ok(
			        Map.of("token", login.getIdToken())
			    );
	}
	
	@PostMapping("/create-group")
	public Group createGroup(@RequestBody GroupBean groupBean) throws InterruptedException, ExecutionException, FirebaseAuthException{
		System.out.println(groupBean.getMembersId());
		return groupService.createGroup(groupBean);
	}
	
	@GetMapping("/groups")
	public List<Group> getGroups(@RequestParam String email) throws InterruptedException, ExecutionException {
		return groupService.getGroupsByEmail(email);
	}
	
	@GetMapping("/groups-by-id")
	public Group getGroupById(@RequestParam String groupId) throws InterruptedException, ExecutionException {
		return groupService.getGroupById(groupId);
	}
	
	@PostMapping("/add-expense")
	public ExpenseBean addExpense(@RequestBody ExpenseBean expenseBean) throws InterruptedException, ExecutionException {
		return expenseService .createExpense(expenseBean);
	}
	
	@GetMapping("/groups/{groupId}/expenses")
	public List<ExpenseBean> getGroupExpenses(@PathVariable String groupId) throws InterruptedException, ExecutionException{
		return expenseService.getExpensesByGroupId(groupId);
	}
	
	@GetMapping("/groups/{groupId}/dashboard")
	public ResponseEntity<GroupDashboardBean> getDashboard(@PathVariable String groupId) throws InterruptedException, ExecutionException {
		GroupDashboardBean dashboard=expenseService.getDashboard(groupId);
		return ResponseEntity.ok(dashboard);
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<String> testGetEndpoint(){
		return ResponseEntity.ok("test get Endpoint is working");
	}
	
	@ExceptionHandler(FirebaseAuthException.class)
	public ResponseEntity<?> handleAuthError(FirebaseAuthException e) {
	    return ResponseEntity
	        .status(HttpStatus.BAD_REQUEST)
	        .body(e.getMessage());
	}

}
