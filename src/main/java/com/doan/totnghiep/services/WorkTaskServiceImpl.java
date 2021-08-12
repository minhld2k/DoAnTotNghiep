package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.WorkTask;
import com.doan.totnghiep.repositories.WorkTaskRepository;

@Service
public class WorkTaskServiceImpl implements WorkTaskService{
	@Autowired
	WorkTaskRepository repository;
	
	@Override
	public void save(WorkTask item) {
		this.repository.save(item);
	}
	
	@Override
	public WorkTask getWorkTask(long workTaskId) {
		return this.repository.findById(workTaskId).get();
	}
}
