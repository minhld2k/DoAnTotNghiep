package com.doan.totnghiep.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.ChucNang;
import com.doan.totnghiep.repositories.ChucNangRepository;

@Service
public class ChucNangServiceImpl implements ChucNangService {
	@Autowired
	ChucNangRepository repository;
	
	@Override
	public void add(ChucNang chucnang) {
		this.repository.save(chucnang);
	}

	@Override
	public void update(ChucNang chucnang) {
		this.repository.save(chucnang);
	}
	
	@Override
	public ChucNang getChucNang(long id) {
		return this.repository.findById(id).get();
	}
	
	@Override
	public List<ChucNang> getAllChucNang() {
		return this.repository.getAllChucNang();
	}
	
	@Override
	public int getAllChucNangCount() {
		return this.repository.getAllChucNangCount();
	}
	
	@Override
	public List<ChucNang> findByKey(String key){
		return this.repository.findByKey(key);
	}
}
