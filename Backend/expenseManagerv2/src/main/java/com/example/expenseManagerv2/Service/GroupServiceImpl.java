package com.example.expenseManagerv2.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expenseManagerv2.Bean.GroupBean;
import com.example.expenseManagerv2.dao.GroupDAO;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	GroupDAO groupDAO;

	@Override
	public String createGroup(GroupBean groupBean) throws InterruptedException, ExecutionException {
		return groupDAO.createGroup(groupBean);
		
	}

}
