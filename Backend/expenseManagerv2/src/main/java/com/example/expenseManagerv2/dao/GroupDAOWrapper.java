package com.example.expenseManagerv2.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.expenseManagerv2.Bean.Group;
import com.example.expenseManagerv2.Bean.GroupBean;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;


@Repository
public class GroupDAOWrapper implements GroupDAO{
	@Autowired
	 private Firestore firestore;

	@Override
	public Group createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException {
		DocumentReference docRef = firestore.collection("groups").document();
		HashMap<String, Object> group= new HashMap<>();
		group.put("name",groupBean.getName());
		group.put("groupId",docRef.getId());
		group.put("createdAt",Instant.now().toString());
		group.put("membersId",groupBean.getMembersEmail());
		group.put("createdBy", groupBean.getCreatedBy());
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("groups").document(docRef.getId()).set(group);
		ApiFuture<DocumentSnapshot> readFuture = docRef.get();
	    DocumentSnapshot snapshot = readFuture.get();
		return snapshot.toObject(Group.class);
	}

	@Override
	public List<Group> getGroupsByEmail(String email) throws InterruptedException, ExecutionException {
		CollectionReference groupRef= firestore.collection("groups");
		Query query= groupRef.whereArrayContains("membersId", email);
		ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Group> groups = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Group group = doc.toObject(Group.class);
            group.setGroupId(doc.getId()); // important
            groups.add(group);
        }

        return groups;
	}
	 

}
