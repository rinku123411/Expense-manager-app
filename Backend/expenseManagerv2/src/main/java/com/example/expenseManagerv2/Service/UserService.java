package com.example.expenseManagerv2.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.UserBean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class UserService {
public String createUser(UserBean userBean) throws InterruptedException, ExecutionException {
	Firestore dbFirestore = FirestoreClient.getFirestore();
	ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("users").document(userBean.getUserId()).set(userBean);
	return collectionApiFuture.get().getUpdateTime().toString();
	}
public UserBean getuser(String userId) throws InterruptedException, ExecutionException {
	Firestore dbFirestore = FirestoreClient.getFirestore();
	DocumentReference documentReference= dbFirestore.collection("users").document(userId);
	ApiFuture<DocumentSnapshot> future =documentReference.get();
	DocumentSnapshot document= future.get();
	UserBean userBean;
	if(document.exists()) {
		userBean=document.toObject(UserBean.class);
		return userBean;
	}
	return null;
}

}
