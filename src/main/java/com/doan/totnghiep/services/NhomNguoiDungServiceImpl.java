package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.NhomNguoiDung;
import com.doan.totnghiep.repositories.NhomNguoiDungRepository;

@Service
public class NhomNguoiDungServiceImpl implements NhomNguoiDungService{
	@Autowired
	NhomNguoiDungRepository repository;
	
	@Override
	public void add(NhomNguoiDung nhom) {
		this.repository.save(nhom);
	}

	@Override
	public void update(NhomNguoiDung nhom) {
		this.repository.save(nhom);
	}
	
	@Override
	public NhomNguoiDung getNhomNguoiDung(long id) {
		return this.repository.findById(id).get();
	}
	
}
