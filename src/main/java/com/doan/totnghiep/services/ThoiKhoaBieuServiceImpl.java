package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.repositories.ThoiKhoaBieuRepository;

@Service
public class ThoiKhoaBieuServiceImpl implements ThoiKhoaBieuService {
	
	@Autowired
	ThoiKhoaBieuRepository repository;
	
	@Override
	public void update(ThoiKhoaBieu item) {
		this.repository.save(item);
	}
	
	@Override
	public ThoiKhoaBieu getThoiKhoaBieu(long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(ThoiKhoaBieu entity) {
		repository.delete(entity);
	}
}
