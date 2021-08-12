package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.WorkTaskDetail;
import com.doan.totnghiep.repositories.WorkTaskDetailRepository;

@Service
public class WorkTaskDetailServiceImpl implements WorkTaskDetailService{
	@Autowired
	WorkTaskDetailRepository repository;
	
	@Override
	public void save(WorkTaskDetail item) {
		this.repository.save(item);
	}
	
	@Override
	public WorkTaskDetail getWorkTaskDetail(long workTaskId) {
		return this.repository.findById(workTaskId).get();
	}
	
	@Override
	public void delete(WorkTaskDetail wt) {
		this.repository.delete(wt);
	}
}
