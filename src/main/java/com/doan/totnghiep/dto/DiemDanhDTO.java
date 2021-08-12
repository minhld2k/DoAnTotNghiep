package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiemDanhDTO {
	private long id;
	private int denLop;
	private int kienThuc;
	private int thucHanh;
	private String ghiChu;
	private long tkbId;
	private long sinhVienId;

}
