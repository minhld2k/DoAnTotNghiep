package com.doan.totnghiep.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThongBaoDTO {
	private long id;
	private String tieuDe;
	private String noiDung;
	private int trangThai;
	private int loai;
	private long nguoiGuiId;
}
