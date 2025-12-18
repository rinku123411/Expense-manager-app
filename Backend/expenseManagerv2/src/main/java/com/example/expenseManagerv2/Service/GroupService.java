package com.example.expenseManagerv2.Service;

import java.util.concurrent.ExecutionException;

import com.example.expenseManagerv2.Bean.GroupBean;

public interface GroupService {
	public String createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException;

}
