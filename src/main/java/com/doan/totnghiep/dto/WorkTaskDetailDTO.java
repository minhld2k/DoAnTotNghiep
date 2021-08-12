package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkTaskDetailDTO {
	private long id;
	private String ten;
	private int thutu;
	private long workTaskId;
}
