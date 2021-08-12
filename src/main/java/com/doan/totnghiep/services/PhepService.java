package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.Phep;

public interface PhepService {

	Phep getPhep(long id);

	void update(Phep item);

}
