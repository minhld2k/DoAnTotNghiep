package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.NhatKy;
import com.doan.totnghiep.repositories.NhatKyRepository;

@Service
public class NhatKyServiceImpl implements NhatKyService {
	@Autowired
	NhatKyRepository repository;
	
	@Override
	public void add(NhatKy nhatKy) {
		this.repository.save(nhatKy);
	}
}
