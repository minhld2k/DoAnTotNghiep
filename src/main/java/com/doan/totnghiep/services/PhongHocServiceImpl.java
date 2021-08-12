package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.PhongHoc;
import com.doan.totnghiep.repositories.PhongHocRepository;

@Service
public class PhongHocServiceImpl implements PhongHocService{
	
	@Autowired
	PhongHocRepository repository;

	@Override
	public void update(PhongHoc item) {
		this.repository.save(item);
	}
	
	@Override
	public PhongHoc getPhongHoc(long id) {
		return this.repository.findById(id).get();
	}
}
