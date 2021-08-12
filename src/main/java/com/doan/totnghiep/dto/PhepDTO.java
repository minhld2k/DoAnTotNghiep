package com.doan.totnghiep.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhepDTO {
	private long id;
	private Date tuNgay;
	private Date denNgay;
	private int soNgayNghi;
	private String lyDo;
	private int trangThai;
}
