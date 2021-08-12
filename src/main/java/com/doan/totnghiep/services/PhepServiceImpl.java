package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.Phep;
import com.doan.totnghiep.repositories.PhepRepository;

@Service
public class PhepServiceImpl implements PhepService {
	@Autowired
	PhepRepository repository;
	
	@Override
	public void update(Phep item) {
		this.repository.save(item);
	}
	
	@Override
	public Phep getPhep(long id) {
		return this.repository.findById(id).get();
	}

}
