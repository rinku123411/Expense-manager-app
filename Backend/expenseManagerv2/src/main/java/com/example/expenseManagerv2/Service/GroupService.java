package com.example.expenseManagerv2.Service;

import java.util.concurrent.ExecutionException;

import com.example.expenseManagerv2.Bean.Group;
import com.example.expenseManagerv2.Bean.GroupBean;

public interface GroupService {
	public Group createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException;

}
