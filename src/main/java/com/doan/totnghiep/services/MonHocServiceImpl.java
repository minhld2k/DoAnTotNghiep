package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.MonHoc;
import com.doan.totnghiep.repositories.MonHocRepository;

@Service
public class MonHocServiceImpl implements MonHocService {
	@Autowired
	MonHocRepository repository;
	
	@Override
	public void update(MonHoc item) {
		this.repository.save(item);
	}
	
	@Override
	public MonHoc getMonHoc(long id) {
		return this.repository.findById(id).get();
	}
}
