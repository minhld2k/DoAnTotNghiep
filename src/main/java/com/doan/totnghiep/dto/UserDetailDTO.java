package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailDTO {
	private long id;
	private String hoTen;
	private int gioiTinh;
	private String diaChi;
	private String phone;
	private String email;
	private long nhomId;

}
