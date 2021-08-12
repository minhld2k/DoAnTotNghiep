package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.LopHoc;

public interface LopHocService {

	LopHoc getLopHoc(long id);

	void update(LopHoc lop);

	void add(LopHoc lop);

}
