package com.doan.totnghiep.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.dto.DiemThiDTO;
import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.entities.NgoaiHeThong;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
public class BaoCaoThongKeController {
	@Autowired
	ParamService param;
	
	@Autowired
	CustomRepository custom;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	SinhVienService sinhVienService;
	
	@RequestMapping("/baocao/viewBaoCao")
	public String viewBaoCao() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				List<LopHoc> lstData = this.custom.getAllLopHoc("", -1, -1, -1);
				List<KhoaHoc> lstKhoa = this.custom.getAllKhoaHoc("", -1, -1, -1, -1);
				
				param.setAttribute("listLopHoc", lstData);
				param.setAttribute("listKhoa", lstKhoa);
				return "baocao.view";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/baocao/loadData")
	public String loadData() {
		int hocKy = param.getInt("hocKy", 0);
		long lopId = param.getLong("lopId", 0);
		String maSV = param.getString("maSV", "");
		long khoaId = param.getLong("khoaId", 0);
		List<Object[]> lsSinhVien = new ArrayList<Object[]>();
		if (!maSV.equals("")) {
			List<SinhVien> lsSV = this.sinhVienService.findByMaSV(maSV);
			if (lsSV.size() > 0) {
				SinhVien sv = lsSV.get(0);
				lopId = sv.getLop().getId();
				Object[] ob = new Object[2];
				ob[0] = sv.getId();
				ob[1] = sv.getHoTen();
				lsSinhVien.add(ob);
			}
		}else if (lopId > 0) {
			lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
		}else if (khoaId > 0) {
			List<LopHoc> lsLop = this.custom.getAllLopHoc("", khoaId, -1, -1);
			for (int i = 0; i < lsLop.size(); i++) {
				lsSinhVien.addAll(this.custom.getAllSinhVienByLopId(lsLop.get(i).getId()));
			}
		}
		
		param.setAttribute("listSinhVien", lsSinhVien);
		param.setAttribute("lopId", lopId);
		if (hocKy != 11) {
			List<Object[]> lsMon = this.custom.getMonHocByLopAndHocKy(hocKy, lopId);
			param.setAttribute("loai", 1);
			param.setAttribute("lsMon", lsMon);
		}else {
			param.setAttribute("loai", 2);
		}
		return "baocao.hocKy";
	}
	
	@RequestMapping(value = "/baocao/loadDiem", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<DiemThiDTO>> loadDiem() {
		int hocKy = param.getInt("hocKy", 0);
		long lopId = param.getLong("lopId", 0);
		String maSV = param.getString("maSV", "");
		int tinhTrang = param.getInt("tinhTrang", 0);
		long khoaId = param.getLong("khoaId", 0);
		List<Object[]> lsSinhVien = new ArrayList<Object[]>();
		if (!maSV.equals("")) {
			List<SinhVien> lsSV = this.sinhVienService.findByMaSV(maSV);
			if (lsSV.size() > 0) {
				SinhVien sv = lsSV.get(0);
				lopId = sv.getLop().getId();
				Object[] ob = new Object[2];
				ob[0] = sv.getId();
				ob[1] = sv.getHoTen();
				lsSinhVien.add(ob);
			}
		}else if (lopId > 0) {
			lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
		}else if (khoaId > 0) {
			List<LopHoc> lsLop = this.custom.getAllLopHoc("", khoaId, -1, -1);
			for (int i = 0; i < lsLop.size(); i++) {
				lsSinhVien.addAll(this.custom.getAllSinhVienByLopId(lsLop.get(i).getId()));
			}
		}
		
		List<DiemThiDTO> lsDiemThi = new ArrayList<DiemThiDTO>();
		if (lsSinhVien.size() > 0) {
			for (int i = 0; i < lsSinhVien.size(); i++) {
				if (hocKy != 11) {
					List<Map<String, String>> lsMap = new ArrayList<Map<String,String>>();
					DiemThiDTO dto = new DiemThiDTO();
					List<Object[]> lsDiem = this.custom.getDiemThi(hocKy, Long.parseLong(lsSinhVien.get(i)[0].toString()));
					double dtb = 0;
					int noMon = 0;
					if (lsDiem.size() > 0) {
						for (int j = 0; j < lsDiem.size(); j++) {
							Map<String, String> map = new HashMap<String, String>();
							double diem = CommonUtil.getDiemTBMon(lsDiem.get(j)[2].toString(), lsDiem.get(j)[3].toString());
							if (diem < 5) {
								noMon++;
							}
							map.put("monId",lsDiem.get(j)[0].toString() );
							map.put("diem", String.format("%.2f", diem));
							lsMap.add(map);
						}
						dtb = CommonUtil.getDiemTBHocKy(lsDiem);
					}
					dto.setDiem(lsMap);
					dto.setSinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
					dto.setDtb(String.format("%.2f", dtb));
					dto.setSoMonNo(noMon);
					dto.setXepLoai(CommonUtil.getXepLoai(dtb));
					lsDiemThi.add(dto);
				}else {
					List<Map<String, String>> lsMap = new ArrayList<Map<String,String>>();
					double dtbHk = 0; // dtb 7 hk
					int noMon = 0;
					for(int hk = 1; hk<=7;hk ++) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("hocKy", String.valueOf(hk));
						double dtb = 0;
						List<Object[]> lsDiem = this.custom.getDiemThi(hk, Long.parseLong(lsSinhVien.get(i)[0].toString()));
						if (lsDiem.size() > 0) {
							for (int j = 0; j < lsDiem.size(); j++) {
								double diem = CommonUtil.getDiemTBMon(lsDiem.get(j)[2].toString(), lsDiem.get(j)[3].toString());
								if (diem < 5) {
									noMon++;
								}
							}
							dtb = CommonUtil.getDiemTBHocKy(lsDiem);
							dtbHk += dtb;
						}
						map.put("dtb", String.format("%.2f", dtb));
						lsMap.add(map);
					}
					
					//diem ngoaiHeThong
					NgoaiHeThong nht = this.custom.getDiemBySinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
					String thucHien = nht.getThucHien() == null ? "0" : nht.getThucHien();
					String baoCao = nht.getBaoCao() == null ? "0" : nht.getBaoCao();
					String chinhTri = nht.getChinhTri()== null ? "0" : nht.getChinhTri();
					
					double diemDoAn = (Double.parseDouble(thucHien) * 60  + Double.parseDouble(baoCao) * 40)/100;
					noMon = diemDoAn < 5 ? noMon+1 : noMon;
					double diemTotNgiep = (dtbHk/7 + diemDoAn *2 + Double.parseDouble(chinhTri)) / 4;
					if (tinhTrang == 0) {
						DiemThiDTO dto = new DiemThiDTO();
						dto.setDiem(lsMap);
						dto.setSoMonNo(noMon);
						dto.setDtb(String.format("%.2f", diemTotNgiep));
						dto.setSinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
						dto.setXepLoai(CommonUtil.getXepLoai(diemTotNgiep));
						dto.setTinhTrang(diemTotNgiep >= 5 && noMon == 0 ? "Đã tốt nghiệp" : "Chưa tốt nghiệp");
						dto.setDiemChinhTri(chinhTri);
						dto.setDiemDoAn(String.format("%.2f", diemDoAn));
						lsDiemThi.add(dto);
					}else if(tinhTrang == 1) {
						if (diemTotNgiep >= 5 && noMon == 0) {
							DiemThiDTO dto = new DiemThiDTO();
							dto.setDiem(lsMap);
							dto.setSoMonNo(noMon);
							dto.setDtb(String.format("%.2f", diemTotNgiep));
							dto.setSinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
							dto.setXepLoai(CommonUtil.getXepLoai(diemTotNgiep));
							dto.setTinhTrang(diemTotNgiep >= 5 && noMon == 0 ? "Đã tốt nghiệp" : "Chưa tốt nghiệp");
							dto.setDiemChinhTri(chinhTri);
							dto.setDiemDoAn(String.format("%.2f", diemDoAn));
							lsDiemThi.add(dto);
						}
					}else if(tinhTrang == 2) {
						if (!(diemTotNgiep >= 5 && noMon == 0)) {
							DiemThiDTO dto = new DiemThiDTO();
							dto.setDiem(lsMap);
							dto.setSoMonNo(noMon);
							dto.setDtb(String.format("%.2f", diemTotNgiep));
							dto.setSinhVienId(Long.parseLong(lsSinhVien.get(i)[0].toString()));
							dto.setXepLoai(CommonUtil.getXepLoai(diemTotNgiep));
							dto.setTinhTrang(diemTotNgiep >= 5 && noMon == 0 ? "Đã tốt nghiệp" : "Chưa tốt nghiệp");
							dto.setDiemChinhTri(chinhTri);
							dto.setDiemDoAn(String.format("%.2f", diemDoAn));
							lsDiemThi.add(dto);
						}
					}
				}
			}
		}
		return new ResponseEntity<>(lsDiemThi,HttpStatus.OK);
	}
}
