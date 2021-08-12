package com.doan.totnghiep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.ThongBao;
import com.doan.totnghiep.repositories.ThongBaoRepository;

@Service
public class ThongBaoServiceImpl implements ThongBaoService {
	@Autowired
	ThongBaoRepository repository;
	
	@Override
	public ThongBao update(ThongBao item) {
		return this.repository.save(item);
	}
	
	@Override
	public ThongBao getThongBao(long id) {
		return this.repository.findById(id).get();
	}
}
