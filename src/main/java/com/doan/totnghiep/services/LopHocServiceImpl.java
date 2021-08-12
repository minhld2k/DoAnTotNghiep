package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.repositories.LopHocRepository;

@Service
public class LopHocServiceImpl implements LopHocService{
	
	@Autowired
	LopHocRepository repository;
	
	@Override
	public void add(LopHoc lop) {
		this.repository.save(lop);
	}

	@Override
	public void update(LopHoc lop) {
		this.repository.save(lop);
	}
	
	@Override
	public LopHoc getLopHoc(long id) {
		return this.repository.findById(id).get();
	}
}
