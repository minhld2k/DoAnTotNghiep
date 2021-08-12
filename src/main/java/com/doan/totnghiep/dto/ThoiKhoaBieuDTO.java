package com.doan.totnghiep.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThoiKhoaBieuDTO {
	private long id;
	private int cahoc;
	private Date ngay;
	private long monid;
	private String tenmon;
	private long lopid;
	private String tenlop;
	private long phongid;
	private String tenphong;
	private String mota;
}
