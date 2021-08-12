package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.NhomNguoiDung;

public interface NhomNguoiDungService {

	NhomNguoiDung getNhomNguoiDung(long id);

	void update(NhomNguoiDung nhom);

	void add(NhomNguoiDung nhom);

}
