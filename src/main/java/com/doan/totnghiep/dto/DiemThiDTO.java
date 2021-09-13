package com.doan.totnghiep.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiemThiDTO {
	private long sinhVienId;
	private String dtb;
	private String xepLoai;
	private int soMonNo;
	private List<Map<String, String>> diem;
	private String tinhTrang;
	private String diemDoAn;
	private String diemChinhTri;
}
