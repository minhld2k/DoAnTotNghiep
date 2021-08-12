package com.doan.totnghiep.services;

import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.SinhVien;

public interface SinhVienService {

	SinhVien getSinhVien(long id);

	void update(SinhVien item);

}
