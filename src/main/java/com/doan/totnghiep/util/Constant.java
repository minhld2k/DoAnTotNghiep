package com.doan.totnghiep.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constant {
	
	public static String getTrangThaiMonHoc(int trangThai) {
		String tenTrangThai = "";
		switch (trangThai) {
		case 0:
			tenTrangThai = "Đang trong quá trình học";
			break;
		case 1:
			tenTrangThai = "Chuẩn bị thi kết thúc môn";
			break;
		case 2:
			tenTrangThai = "Kết thúc môn";
			break;
		default:
			break;
		}
		return tenTrangThai;
	}
	
	public static String getCaHoc(int ca) {
		String tenTrangThai = "";
		switch (ca) {
		case 3:
			tenTrangThai = "Tối";
			break;
		case 1:
			tenTrangThai = "Sáng";
			break;
		case 2:
			tenTrangThai = "Chiều";
			break;
		default:
			break;
		}
		return tenTrangThai;
	}
	
	public static String getDayStringOld(Date date) {
	    DateFormat formatter = new SimpleDateFormat("EEEE", new Locale("vi", "VN"));
	    return formatter.format(date);
	}

}
