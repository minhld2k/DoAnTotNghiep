package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Menu {
	private long id;
	private String ten;
	private String url;
	private String icon;
}
