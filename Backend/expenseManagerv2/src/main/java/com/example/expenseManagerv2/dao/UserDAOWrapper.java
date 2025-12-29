package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.UserBean;
import com.example.expenseManagerv2.Service.AuthService;
import com.example.expenseManagerv2.Bean.LoginBean;
import com.example.expenseManagerv2.Bean.LoginRequestBean;
import com.example.expenseManagerv2.Bean.User;
import com.example.expenseManagerv2.entity.UserEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class UserDAOWrapper implements UserDAO{
	@Autowired
	private Firestore firestore;
	@Autowired
	private AuthService authService;

	@Override
	public Map<String, Object> createUser(UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException {
		UserRecord.CreateRequest request=  new UserRecord.CreateRequest().setEmail(userBean.getEmail()).setPassword(userBean
				.getPassword());
		UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
		String userId= userRecord.getUid();
		String  createdAt= Instant.now().toString();
		HashMap<String, Object> userData= new HashMap<>();
		userData.put("name", userBean.getName());
		userData.put("email", userBean.getEmail());
		userData.put("userId", userId);
		userData.put("createdAt", createdAt);
		
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("users").document(userId).set(userData);
		 LoginRequestBean loginRequestBean= new LoginRequestBean();
		 loginRequestBean.setEmail(userBean.getEmail());
		 loginRequestBean.setPassword(userBean.getPassword());
		LoginBean login = authService.login(loginRequestBean);
		return Map.of(
	            "user", userData,
	            "token", login.getIdToken()
	    );
		}

	@Override
	public User getUser(String userId, String email) throws  ExecutionException, InterruptedException {
		if(email== null && userId== null) {
			return null;
		}
		else if(email==null) {
			DocumentReference documentReference= firestore.collection("users").document(userId);
			ApiFuture<DocumentSnapshot> future =documentReference.get();
			DocumentSnapshot document= future.get();
			User userResponse;
			if(document.exists()) {
				userResponse=document.toObject(User.class);
				return userResponse;
			}
			return null;
			
		}else if(userId== null){
			QuerySnapshot querySnapshot=  firestore.collection("users").whereEqualTo("email", email).limit(1).get().get();
			if (querySnapshot.isEmpty()) {
	            throw new RuntimeException("User not found with email");
	        }
//				ApiFuture<DocumentSnapshot> future =documentReference.get();
				DocumentSnapshot document= querySnapshot.getDocuments().get(0);
				User userResponse;
				if(document.exists()) {
					userResponse=document.toObject(User.class);
					return userResponse;
				}
				return null;
			}
		else {
			DocumentReference documentReference= firestore.collection("users").document(userId);
			ApiFuture<DocumentSnapshot> future =documentReference.get();
			DocumentSnapshot document= future.get();
			User userResponse;
			if(document.exists()) {
				userResponse=document.toObject(User.class);
				return userResponse;
			}
			return null;
		}
		
	}

	@Override
	public User getuserByEmail(String email) throws InterruptedException, ExecutionException {
		QuerySnapshot querySnapshot=  firestore.collection("users").whereEqualTo("email", email).limit(1).get().get();
		if (querySnapshot.isEmpty()) {
            throw new RuntimeException("User not found with email");
        }
//			ApiFuture<DocumentSnapshot> future =documentReference.get();
			DocumentSnapshot document= querySnapshot.getDocuments().get(0);
			User userResponse;
			if(document.exists()) {
				userResponse=document.toObject(User.class);
				return userResponse;
			}
			return null;
	}
}

