package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.QuanTri;
import com.doan.totnghiep.repositories.QuanTriRepository;

@Service
public class QuanTriServiceImpl implements QuanTriService {
	
	@Autowired
	QuanTriRepository repository;
	
	@Override
	public void update(QuanTri item) {
		this.repository.save(item);
	}
	
	@Override
	public QuanTri getQuanTri(long id) {
		return this.repository.findById(id).get();
	}

}
