package com.doan.totnghiep.services;

import java.util.List;

import com.doan.totnghiep.entities.SinhVien;

public interface SinhVienService {

	SinhVien getSinhVien(long id);

	void update(SinhVien item);

	List<SinhVien> findByMaSV(String maSV);

}
