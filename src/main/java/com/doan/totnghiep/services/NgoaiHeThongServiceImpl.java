package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.NgoaiHeThong;
import com.doan.totnghiep.repositories.NgoaiHeThongRepository;

@Service
public class NgoaiHeThongServiceImpl implements NgoaiHeThongService {
	@Autowired
	NgoaiHeThongRepository repository;

	@Override
	public void save(NgoaiHeThong entity) {
		repository.save(entity);
	}
	
	
}
