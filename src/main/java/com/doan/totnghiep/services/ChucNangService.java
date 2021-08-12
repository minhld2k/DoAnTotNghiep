package com.doan.totnghiep.services;

import java.util.List;

import com.doan.totnghiep.entities.ChucNang;

public interface ChucNangService {
	
	void update(ChucNang chucnang);

	void add(ChucNang chucnang);
	
	ChucNang getChucNang(long id);
	
	int getAllChucNangCount();
	
	List<ChucNang> getAllChucNang();

	List<ChucNang> findByKey(String key);

}
