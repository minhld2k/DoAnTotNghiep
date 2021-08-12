package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.DiemThi;
import com.doan.totnghiep.repositories.DiemThiRepository;

@Service
public class DiemThiServiceImpl implements DiemThiService {
	@Autowired
	DiemThiRepository repository;
	
	@Override
	public void add(DiemThi item) {
		this.repository.save(item);
	}
}
