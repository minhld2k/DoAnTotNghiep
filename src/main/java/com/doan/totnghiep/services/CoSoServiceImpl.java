package com.doan.totnghiep.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.CoSo;
import com.doan.totnghiep.repositories.CoSoRepository;

@Service
public class CoSoServiceImpl implements CoSoService {
	@Autowired
	CoSoRepository repository;

	@Override
	public void update(CoSo coso) {
		this.repository.save(coso);
	}

	@Override
	public List<CoSo> findAll() {
		return repository.findAll();
	}

	@Override
	public CoSo getCoSo(long id) {
		return repository.findById(id).get();
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(CoSo entity) {
		repository.delete(entity);
	}
	
}
