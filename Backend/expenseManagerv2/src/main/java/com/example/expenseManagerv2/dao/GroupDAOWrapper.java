package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.GroupBean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

@Repository
public class GroupDAOWrapper implements GroupDAO{
	 private Firestore firestore;

	@Override
	public String createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException {
		DocumentReference docRef = firestore.collection("groups").document();
		HashMap<String, Object> group= new HashMap<>();
		group.put("name",groupBean.getName());
		group.put("groupId",docRef.getId());
		group.put("createdAt",Instant.now().toString());
		group.put("membersId",groupBean.getMembersId());
		group.put("createdBy", groupBean.getCreatedBy());
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("groups").document(docRef.getId()).set(group);
		return collectionApiFuture.get().getUpdateTime().toString();
	}
	 

}
