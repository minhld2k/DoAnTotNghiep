package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.repositories.SinhVienRepository;

@Service
public class SinhVienServiceImpl implements SinhVienService {
	
	@Autowired
	SinhVienRepository repository;
	
	@Override
	public void update(SinhVien item) {
		this.repository.save(item);
	}
	
	@Override
	public SinhVien getSinhVien(long id) {
		return this.repository.findById(id).get();
	}

}
