package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.MonHoc;

public interface MonHocService {

	MonHoc getMonHoc(long id);

	void update(MonHoc item);

}
