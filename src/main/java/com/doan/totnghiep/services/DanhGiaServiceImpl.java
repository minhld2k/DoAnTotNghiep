package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.DanhGia;
import com.doan.totnghiep.repositories.DanhGiaRepository;

@Service
public class DanhGiaServiceImpl implements DanhGiaService{
	@Autowired
	DanhGiaRepository repository;
	
	@Override
	public void save(DanhGia item) {
		this.repository.save(item);
	}
	
	@Override
	public DanhGia getDanhGia(long danhGiaId) {
		return this.repository.findById(danhGiaId).get();
	}
}
