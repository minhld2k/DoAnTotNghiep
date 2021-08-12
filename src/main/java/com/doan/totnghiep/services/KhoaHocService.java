package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.KhoaHoc;

public interface KhoaHocService {

	KhoaHoc getKhoaHoc(long id);

	void update(KhoaHoc khoa);

	void add(KhoaHoc khoa);

}
