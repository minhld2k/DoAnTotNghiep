package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.repositories.KhoaHocRepository;

@Service
public class KhoaHocServiceImpl implements KhoaHocService {
	@Autowired
	KhoaHocRepository repository;
	
	@Override
	public void add(KhoaHoc khoa) {
		this.repository.save(khoa);
	}

	@Override
	public void update(KhoaHoc khoa) {
		this.repository.save(khoa);
	}
	
	@Override
	public KhoaHoc getKhoaHoc(long id) {
		return this.repository.findById(id).get();
	}

}
