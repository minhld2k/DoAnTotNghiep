package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.DiemDanh;
import com.doan.totnghiep.repositories.DiemDanhRepository;

@Service
public class DiemDanhServiceImpl implements DiemDanhService {
	@Autowired
	DiemDanhRepository repository;
	
	@Override
	public void add(DiemDanh diemDanh) {
		this.repository.save(diemDanh);
	}

}
