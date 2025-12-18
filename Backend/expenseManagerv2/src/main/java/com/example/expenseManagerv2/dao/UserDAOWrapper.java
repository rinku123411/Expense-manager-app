package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.UserBean;
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

	@Override
	public String createUser(UserBean userBean) throws InterruptedException, ExecutionException, FirebaseAuthException {
		UserRecord.CreateRequest request=  new UserRecord.CreateRequest().setEmail(userBean.getEmail()).setPassword(userBean
				.getPassword());
		UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
		String userId= userRecord.getUid();
		HashMap<String, Object> userData= new HashMap<>();
		userData.put("name", userBean.getName());
		userData.put("email", userBean.getEmail());
		userData.put("userId", userId);
		userData.put("createdAt", Instant.now().toString());
		
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("users").document(userId).set(userData);
		return collectionApiFuture.get().getUpdateTime().toString();
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
}

