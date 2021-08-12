package com.doan.totnghiep.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.dto.PhepDTO;
import com.doan.totnghiep.dto.ThoiKhoaBieuDTO;
import com.doan.totnghiep.entities.Phep;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.PhepService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.ThoiKhoaBieuService;
import com.doan.totnghiep.services.UserService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping(value = "/sinhvien")
public class SinhVienController {

	@Autowired
	ParamService param;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ThoiKhoaBieuService thoiKhoaBieuService;
	
	@Autowired
	PhepService phepService;
	
	@Autowired
	CustomRepository custom;
	
	@Autowired
	SinhVienService sinhVienService;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/tkb")
	public String thoiKhoaBieu() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				SinhVien sv = this.sinhVienService.getSinhVien(_u.getId());
				param.setAttribute("lopId", sv.getLop().getId());
				return "sv.tkb.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value = "/tkb/load")
	public String loadTKB() {
		long lopId = param.getLong("lopId", 0);
		String tuNgay = param.getString("tuNgay", "");
		String denNgay = param.getString("denNgay", "");
		
		List<ThoiKhoaBieuDTO> lsData =  this.custom.getAllThoiKhoaBieu(lopId, tuNgay, denNgay);
		
		param.setAttribute("TKB", lsData);
		
		return "sv.tkb.listitem";
	}
	
	@RequestMapping(value = "/phep")
	public String listPhep() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				return "sv.phep.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/phep/load")
	public String loadPhep() {
		//user login
		User _u = (User) session.getAttribute("USERLOGIN");
		long userId = _u.getId();
		List<PhepDTO> lsData = this.custom.getAllPhepByUserId(userId);
		
		param.setAttribute("lsPhep", lsData);
		return "sv.phep.listitem";
	}
	
	@RequestMapping(value = "/phep/add")
	public String addPhep() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				return "sv.phep.add";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/phep/save")
	public String savePhep() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if(_u != null) {
			long userId = _u.getId();
			
			Phep phep = new Phep();
			
			String lyDo = param.getString("lyDo", "");
			int soNgay = param.getInt("soNgayNghi", 1);
			String tuNgay = param.getString("tuNgay", "");
			String denNgay = param.getString("denNgay", "");
			
			phep.setDaXoa((byte) 0);
			phep.setLyDo(lyDo);
			phep.setTrangThai(0);
			phep.setSoNgayNghi(soNgay);
			phep.setNgayTao(new Date());
			phep.setUser(_u);
			phep.setNguoiTao(this.sinhVienService.getSinhVien(userId).getHoTen());
			if (!tuNgay.equals("")) {
				Date date = CommonUtil.todate(tuNgay, "yyyy-MM-dd");
				phep.setTuNgay(date);
			}
			
			if (!denNgay.equals("")) {
				Date date = CommonUtil.todate(denNgay, "yyyy-MM-dd");
				phep.setDenNgay(date);
			}
			
			this.phepService.update(phep);
			
			//send thong bao
			Map<String, Object> map = new HashMap<>();
			map.put("lopId", this.sinhVienService.getSinhVien(userId).getLop().getId());
			map.put("loai", 2);
			map.put("nguoiTao", CommonUtil.getTenNguoiDungByUserId(userId, 1));
			map.put("user", _u);
			map.put("phep", phep);
			CommonUtil.sendThongBao(map);
			return "redirect:/sinhvien/phep";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/phep/huy")
	@ResponseBody
	public String huyPhep() {
		long phepId = param.getLong("phepId", 0);
		int kq = 0;
		if (phepId > 0) {
			Phep phep = this.phepService.getPhep(phepId);
			phep.setTrangThai(1);
			phep.setNgaySua(new Date());
			phep.setNguoiSua(phep.getNguoiTao());
			this.phepService.update(phep);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping("/lop")
	public String lopHoc() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				SinhVien sv = this.sinhVienService.getSinhVien(_u.getId());
				param.setAttribute("isThongBao", param.getInt("isThongBao", 0));
				param.setAttribute("LopSV", sv.getLop());
				return "sv.lop.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lop/xemMonHoc")
	public String xemMonHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				SinhVien sv = this.sinhVienService.getSinhVien(u.getId());
				param.setAttribute("listMonHoc", this.custom.getMonHocByLopId(sv.getLop().getId(),-1));
				return "sv.lop.listMon";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lop/viewDiemDanh")
	public String viewDiemDanh() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			SinhVien sv = this.sinhVienService.getSinhVien(_u.getId());
			long lopId = sv.getLop().getId();
			long monId = param.getLong("monId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			List<Object[]> lsTkb = this.custom.getLichDayByLopIdAndMonId(-1, lopId, monId);
			
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("lsTKB", lsTkb);
			param.setAttribute("Lop", sv.getLop());
			return "gv.lop.viewdiemdanh";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lop/viewDiemThi")
	public String viewDiemThi() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			SinhVien sv = this.sinhVienService.getSinhVien(_u.getId());
			long lopId = sv.getLop().getId();
			long monId = param.getLong("monId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("Lop", sv.getLop());
			param.setAttribute("monId", monId);
			return "gv.lop.viewdiemthi";
		}else {
			return "redirect:/login";
		}
	}
}
