package com.example.expenseManagerv2.dao;

import java.util.concurrent.ExecutionException;

import com.example.expenseManagerv2.Bean.Group;
import com.example.expenseManagerv2.Bean.GroupBean;

public interface GroupDAO {
	public Group createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException;

}
