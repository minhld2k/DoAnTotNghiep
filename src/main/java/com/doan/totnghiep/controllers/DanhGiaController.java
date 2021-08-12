package com.doan.totnghiep.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.entities.DanhGia;
import com.doan.totnghiep.entities.MonHoc;
import com.doan.totnghiep.entities.SinhVienDanhGia;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.entities.WorkTask;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.DanhGiaService;
import com.doan.totnghiep.services.MonHocService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping(value = "/danhgia")
public class DanhGiaController {
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CustomRepository custom;
	
	@Autowired
	DanhGiaService danhGiaService;
	
	@Autowired
	MonHocService monService;
	
	@Autowired
	SinhVienService svService;
	
	@RequestMapping(value = "/list")
	public String listKhoaHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long monId = param.getLong("monId", 0);
				param.setAttribute("lsDanhGia", this.custom.getAllDanhGia(monId));
				param.setAttribute("monId", monId);
				return "danhgia.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/addOrEdit")
	public String addOrEditWorkTask() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("danhGiaId", 0);
				long monId = param.getLong("monId", 0);
				String title = "Thêm mới đánh giá";
				DanhGia item = null;
				if (id > 0) {
					item = this.danhGiaService.getDanhGia(id);
					title = "Cập nhật đánh giá";
				}
				session.setAttribute("title", title);
				param.setAttribute("danhGia", item);
				param.setAttribute("monId", monId);
				
				return "danhgia.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/save")
	public String updateLopHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			DanhGia danhGia = new DanhGia();
			long id = param.getLong("id", 0);
			int thuTu = param.getInt("thuTu", 0);
			long monId = param.getLong("monId", 0);
			String tieuDe = param.getString("tieuDe", "");
			String cauHoi = param.getString("cauHoi", "");
			
			if(id > 0) {
				danhGia = this.danhGiaService.getDanhGia(id);
				danhGia.setNguoiSua((String) session.getAttribute("USERNAME"));
				danhGia.setNgaySua(new Date());
			}else {
				danhGia.setNgayTao(new Date());
				danhGia.setNguoiTao((String) session.getAttribute("USERNAME"));
				danhGia.setDaXoa((byte)0);
				if (monId > 0) {
					MonHoc mon = this.monService.getMonHoc(monId);
					if (mon != null) {
						danhGia.setMon(mon);
					}
				}
				
			}
			
			danhGia.setThuTu(thuTu);
			danhGia.setTieuDe(tieuDe);
			danhGia.setCauHoi(cauHoi);
			this.danhGiaService.save(danhGia);
			
			return "redirect:/danhgia/list?monId="+ monId;
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/delete")
	@ResponseBody
	public String deleteKhoaHoc() {
		int kq = 0;
		long id = param.getLong("danhGiaId", 0);
		if (id > 0) {
			DanhGia danhGia = this.danhGiaService.getDanhGia(id);
			danhGia.setDaXoa((byte) 1);
			this.danhGiaService.save(danhGia);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/viewSinhVien")
	public String viewSinhVien() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long monId = param.getLong("monId", 0);
			List<WorkTask> lsData = this.custom.getAllWorkTask(monId);
			List<DanhGia> lsDanhGia = this.custom.getAllDanhGia(monId);
			param.setAttribute("lsWTSV", lsData);
			param.setAttribute("lsDGSV", lsDanhGia);
			param.setAttribute("monId", monId);
			return "danhgia.viewsv";
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/saveWTSV")
	@ResponseBody
	public String saveWTSV(@RequestParam(name = "wtId[]",required = false)List<Long> lsWtId,@RequestParam(name = "ketQua[]",defaultValue = "0") List<Integer> lsKetQua
			,@RequestParam(name = "traLoi[]" ,defaultValue = "null")List<String> lsTraLoi, @RequestParam(name = "danhGiaId[]",required = false)List<Long> lsDanhGiaId) {
		int kq = 0;
		User u = (User) session.getAttribute("USERLOGIN");
		try {
			//luu phan 1 : work/task
			for (int i = 0; i < lsWtId.size(); i++) {
				SinhVienDanhGia svwt = new SinhVienDanhGia();
				svwt.setKetQua(lsKetQua.get(i));
				svwt.setNgayTao(new Date());
				svwt.setSinhVienId(u.getId());
				svwt.setWorkTaskId(lsWtId.get(i));
				svwt.setTraLoi("");
				svwt.setDanhGiaId(0l);
				
				this.custom.updateSinhVienDanhGia(svwt);
			}
			
			//luu phan 2 : danh gia
			for (int i = 0; i < lsDanhGiaId.size(); i++) {
				SinhVienDanhGia svwt = new SinhVienDanhGia();
				svwt.setKetQua(0);
				svwt.setNgayTao(new Date());
				svwt.setSinhVienId(u.getId());
				svwt.setWorkTaskId(0l);
				svwt.setTraLoi(lsTraLoi.get(i).equals("null") ? "" : lsTraLoi.get(i));
				svwt.setDanhGiaId(lsDanhGiaId.get(i));
				
				this.custom.updateSinhVienDanhGia(svwt);
			}
			kq = 1;
		} catch (Exception e) {
			e.printStackTrace();
			kq = 0;
		}
		
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/loadDataWTSV", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDiemThiSV() {
		int loai = param.getInt("loai", 0);
		User u = (User) session.getAttribute("USERLOGIN");
		long monId = param.getLong("monId", 0);
		long userId = u.getId();
		if (loai == 1) {
			userId = param.getLong("sinhVienId", 0);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<SinhVienDanhGia> lsData1 = this.custom.getSinhVienDanhGiaWTBySinhVienId(userId, monId);
		List<SinhVienDanhGia> lsData2 = this.custom.getSinhVienDanhGiaBySinhVienId(userId, monId);
		
		map.put("lsData1", lsData1);
		map.put("lsData2", lsData2);
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/viewPDT")
	public String viewPDT() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long monId = param.getLong("monId", 0);
			long lopId = param.getLong("lopId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			List<WorkTask> lsData = this.custom.getAllWorkTask(monId);
			List<DanhGia> lsDanhGia = this.custom.getAllDanhGia(monId);
			
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("lsWTSV", lsData);
			param.setAttribute("lsDGSV", lsDanhGia);
			param.setAttribute("monId", monId);
			return "danhgia.viewpdt";
		}else {
			return "redirect:/login";
		}
	}

}
