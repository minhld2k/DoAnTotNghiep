package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonHocDTO {
	private long id;
	private String ten;
	private int sotiet;
	private int sobuoi;
	private long giangvienid;
	private String tengiangvien;

}
