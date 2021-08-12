package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.repositories.GiangVienRepository;

@Service
public class GiangVienServiceImpl implements GiangVienService {
	
	@Autowired
	GiangVienRepository repository;
	
	@Override
	public void update(GiangVien item) {
		this.repository.save(item);
	}
	
	@Override
	public GiangVien getGiangVien(long id) {
		return this.repository.findById(id).get();
	}

}
