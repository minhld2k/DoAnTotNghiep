package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.DanhGia;

public interface DanhGiaService {

	DanhGia getDanhGia(long danhGiaId);

	void save(DanhGia item);

}
