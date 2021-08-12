package com.doan.totnghiep.services;

import java.util.List;

import com.doan.totnghiep.entities.CoSo;

public interface CoSoService {

	long count();

	CoSo getCoSo(long id);

	List<CoSo> findAll();

	void update(CoSo coso);

	void delete(CoSo entity);

}
