package com.doan.totnghiep.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.dto.MonHocDTO;
import com.doan.totnghiep.dto.ThoiKhoaBieuDTO;
import com.doan.totnghiep.dto.UserDTO;
import com.doan.totnghiep.dto.UserDetailDTO;
import com.doan.totnghiep.entities.ChucNang;
import com.doan.totnghiep.entities.CoSo;
import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.entities.MonHoc;
import com.doan.totnghiep.entities.NhomNguoiDung;
import com.doan.totnghiep.entities.PhongHoc;
import com.doan.totnghiep.entities.QuanTri;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.CoSoService;
import com.doan.totnghiep.services.GiangVienService;
import com.doan.totnghiep.services.KhoaHocService;
import com.doan.totnghiep.services.LopHocService;
import com.doan.totnghiep.services.MonHocService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.PhongHocService;
import com.doan.totnghiep.services.QuanTriService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.ThoiKhoaBieuService;
import com.doan.totnghiep.services.UserService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping(value = "/phongdaotao/")
public class PhongDaoTaoController {
	@Autowired
	CustomRepository custom;
	
	@Autowired
	KhoaHocService khoaHocService;
	
	@Autowired
	LopHocService lopHocService;
	
	@Autowired
	CoSoService coSoService;
	
	@Autowired
	MonHocService monHocService;
	
	@Autowired
	PhongHocService phongHocService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SinhVienService sinhVienService;
	
	@Autowired
	GiangVienService giangVienService;
	
	@Autowired
	QuanTriService quanTriService;
	
	@Autowired
	LopHocService lopService;
	
	@Autowired
	ThoiKhoaBieuService thoiKhoaBieuService;
	
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@PostMapping(value = "/khoahoc/save")
	public String updateKhoaHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			KhoaHoc khoa = new KhoaHoc();
			long id = param.getLong("id", 0);
			String name = param.getString("tenKhoa", "");
			int thangStart = param.getInt("thangStart", 0);
			int namStart = param.getInt("namStart", 0);
			int thangEnd = param.getInt("thangEnd", 0);
			int namEnd = param.getInt("namEnd", 0);
			
			if(id > 0) {
				khoa = this.khoaHocService.getKhoaHoc(id);
				khoa.setNguoiSua((String) session.getAttribute("USERNAME"));
				khoa.setNgaySua(new Date());
			}else {
				khoa.setNgayTao(new Date());
				khoa.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			
			khoa.setDaXoa((byte)0);
			khoa.setTen(name);
			khoa.setThangBatDau(thangStart);
			khoa.setNamBatDau(namStart);
			khoa.setThangKetThuc(thangEnd);
			khoa.setNamKetThuc(namEnd);
			
			this.khoaHocService.update(khoa);
			
			return "redirect:/phongdaotao/khoahoc/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/khoahoc/list")
	public String listKhoaHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				return "khoahoc.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/khoahoc/loadmore")
	public String loadKhoaHoc() {
		String tenKhoa = param.getString("tenKhoa", "");
		int thangStart = param.getInt("thangStart", 0);
		int namStart = param.getInt("namStart", 0);
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		
		List<KhoaHoc> lstKhoaHoc = this.custom.getAllKhoaHoc(tenKhoa, thangStart, namStart, count, delta);
		int totalCount = this.custom.getAllKhoaHocCount(tenKhoa, thangStart, namStart);
		
		param.setAttribute("listKhoaHoc", lstKhoaHoc);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "khoahoc.listitem";
		
	}
	
	@RequestMapping(value = "/khoahoc/addOrEdit")
	public String addOrEditKhoaHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("khoaId", 0);
				String title = "Thêm mới khóa học";
				KhoaHoc khoa = new KhoaHoc();
				if (id > 0) {
					khoa = this.khoaHocService.getKhoaHoc(id);
					title = "Cập nhật khóa học";
				}
				session.setAttribute("khoaHoc", khoa);
				session.setAttribute("title", title);
				
				return "khoahoc.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/khoahoc/delete")
	@ResponseBody
	public String deleteKhoaHoc() {
		int kq = 0;
		long id = param.getLong("khoaId", 0);
		if (id > 0) {
			KhoaHoc khoa = this.khoaHocService.getKhoaHoc(id);
			khoa.setDaXoa((byte) 1);
			this.khoaHocService.update(khoa);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	// quản lý lớp học
	@PostMapping(value = "/lophoc/save")
	public String updateLopHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			LopHoc lop = new LopHoc();
			long id = param.getLong("id", 0);
			String name = param.getString("tenLop", "");
			long khoaHocId = param.getLong("khoaHocId", 0);
			
			if(id > 0) {
				lop = this.lopHocService.getLopHoc(id);
				lop.setNguoiSua((String) session.getAttribute("USERNAME"));
				lop.setNgaySua(new Date());
			}else {
				lop.setNgayTao(new Date());
				lop.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			
			lop.setDaXoa((byte)0);
			lop.setTen(name);
			
			KhoaHoc khoaHoc = new KhoaHoc();
			if(khoaHocId > 0) {
				khoaHoc = this.khoaHocService.getKhoaHoc(khoaHocId);
				if(khoaHoc != null) {
					lop.setKhoaHoc(khoaHoc);
				}
			}
			
			this.lopHocService.update(lop);
			
			return "redirect:/phongdaotao/lophoc/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lophoc/list")
	public String listLopHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				return "lophoc.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lophoc/loadmore")
	public String loadLopHoc() {
		String tenLop = param.getString("tenLop", "");
		long khoaHocid = param.getLong("khoaHocId", 0);
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		
		List<LopHoc> lstData = this.custom.getAllLopHoc(tenLop, khoaHocid, count, delta);
		int totalCount = this.custom.getAllLopHocCount(tenLop, khoaHocid);
		
		param.setAttribute("listLopHoc", lstData);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "lophoc.listitem";
		
	}
	
	@RequestMapping(value = "/lophoc/addOrEdit")
	public String addOrEditLopHoc(HttpSession session) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("lopId", 0);
				String title = "Thêm mới lớp học";
				LopHoc lop = new LopHoc();
				if (id > 0) {
					lop = this.lopHocService.getLopHoc(id);
					title = "Cập nhật lớp học";
				}
				
				List<KhoaHoc> lstKhoaHoc = this.custom.getAllKhoaHoc("", 0, 0, -1, -1);
				session.setAttribute("listKhoaHoc", lstKhoaHoc);
				session.setAttribute("lopHoc", lop);
				session.setAttribute("title", title);
				
				return "lophoc.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/lophoc/delete")
	@ResponseBody
	public String deleteLopHoc() {
		int kq = 0;
		long id = param.getLong("lopId", 0);
		if (id > 0) {
			LopHoc lop = this.lopHocService.getLopHoc(id);
			lop.setDaXoa((byte) 1);
			this.lopHocService.update(lop);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/lophoc/xemMonHoc")
	public String xemMonHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long lopId = param.getLong("lopId", 0);
				param.setAttribute("listMonHoc", this.custom.getMonHocByLopId(lopId,-1));
				param.setAttribute("lopId", lopId);
				return "lophoc.listMon";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lophoc/searchMon")
	public String searchMon() {
		String key = param.getString("key", "");
		param.setAttribute("listMonHocDTO", this.custom.getAllMonHocDTO(key, -1, -1));
		return "lophoc.searchMon";
	}
	
	@RequestMapping(value = "/lophoc/addMon", produces = "application/json",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> findbyId() {
		Map<String, String> map = new HashMap<String, String>();
		long lopId = param.getLong("lopId", 0);
		long monId = param.getLong("monId", 0);
		if (this.custom.getMonHoc(lopId, monId) > 0) {
			map.put("kq", "0");
		}else {
			JSONArray tableValue = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("lopid", lopId);
			json.put("monid", monId);
			json.put("trangthai", 0);
			tableValue.put(json);
			
			List<String> tableCol = new ArrayList<>();
			tableCol.add("lopid");
			tableCol.add("monid");
			tableCol.add("trangthai");
			this.custom.insert("qlsv_lophoc_monhoc", tableCol, tableValue);
			
			map.put("kq", "1");
		}
		
		return map;
	}
	
	@PostMapping(value = "/lophoc/deleteMon")
	@ResponseBody
	public String deleteLopHocMon() {
		int kq = 0;
		long lopId = param.getLong("lopId", 0);
		long monId = param.getLong("monId", 0);
		if (lopId > 0 && monId > 0) {
			this.custom.deleteMon(lopId, monId);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	// quản lý cơ sở
	@PostMapping(value = "/coso/save")
	public String updateCoSo() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			CoSo item = new CoSo();
			long id = param.getLong("id", 0);
			String name = param.getString("tenCoSo", "");
			String diaChi = param.getString("diaChi", "");
			
			if(id > 0) {
				item = this.coSoService.getCoSo(id);
				item.setNguoiSua((String) session.getAttribute("USERNAME"));
				item.setNgaySua(new Date());
			}else {
				item.setNgayTao(new Date());
				item.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			
			item.setDaXoa((byte)0);
			item.setTen(name);
			item.setDiaChi(diaChi);
			
			this.coSoService.update(item);
			
			return "redirect:/phongdaotao/coso/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/coso/list")
	public String listCoSo() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				List<CoSo> lstData = this.coSoService.findAll();
				param.setAttribute("listCoSo", lstData);
				return "coso.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/coso/addOrEdit")
	public String addOrEditCoSo() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("coSoId", 0);
				String title = "Thêm mới cơ sở";
				CoSo item = new CoSo();
				if (id > 0) {
					item = this.coSoService.getCoSo(id);
					title = "Cập nhật cơ sở";
				}
				session.setAttribute("coSo", item);
				session.setAttribute("title", title);
				
				return "coso.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/coso/delete")
	@ResponseBody
	public String deleteCoSo() {
		int kq = 0;
		long id = param.getLong("coSoId", 0);
		if (id > 0) {
			CoSo item = this.coSoService.getCoSo(id);
			this.coSoService.delete(item);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	// quản lý phòng học
	@PostMapping(value = "/phonghoc/save")
	public String updatePhong() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			PhongHoc item = new PhongHoc();
			long id = param.getLong("id", 0);
			String name = param.getString("ten", "");
			long coSoId = param.getLong("coSoId", 0);
			
			if(id > 0) {
				item = this.phongHocService.getPhongHoc(id);
				item.setNguoiSua((String) session.getAttribute("USERNAME"));
				item.setNgaySua(new Date());
			}else {
				item.setNgayTao(new Date());
				item.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			
			item.setDaXoa((byte)0);
			item.setTen(name);
			
			CoSo soSo = new CoSo();
			if(coSoId > 0) {
				soSo = this.coSoService.getCoSo(coSoId);
				if(soSo != null) {
					item.setCoSo(soSo);
				}
			}
			
			this.phongHocService.update(item);
			
			return "redirect:/phongdaotao/phonghoc/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/phonghoc/list")
	public String listPhong() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				List<CoSo> lstCoSo = this.coSoService.findAll();
				session.setAttribute("listCoSo", lstCoSo);
				return "phonghoc.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/phonghoc/loadmore")
	public String loadPhong() {
		String tenPhong = param.getString("ten", "");
		long coSoId = param.getLong("coSoId", 0);
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		
		List<PhongHoc> lstData = this.custom.getAllPhongHoc(tenPhong, coSoId, count, delta);
		int totalCount = this.custom.getAllPhongHocCount(tenPhong, coSoId);
		
		param.setAttribute("listPhongHoc", lstData);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "phonghoc.listitem";
		
	}
	
	@RequestMapping(value = "/phonghoc/addOrEdit")
	public String addOrEditPhong() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("phongId", 0);
				String title = "Thêm mới phòng học";
				PhongHoc item = new PhongHoc();
				if (id > 0) {
					item = this.phongHocService.getPhongHoc(id);
					title = "Cập nhật phòng học";
				}
				
				List<CoSo> lstCoSo = this.coSoService.findAll();
				session.setAttribute("listCoSo", lstCoSo);
				session.setAttribute("phongHoc", item);
				session.setAttribute("title", title);
				
				return "phonghoc.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/phonghoc/delete")
	@ResponseBody
	public String deletePhong() {
		int kq = 0;
		long id = param.getLong("phongId", 0);
		if (id > 0) {
			PhongHoc item = this.phongHocService.getPhongHoc(id);
			item.setDaXoa((byte) 1);
			this.phongHocService.update(item);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	// quản lý người dùng
	@PostMapping(value = "/user/save")
	public String updateNguoiDung(@RequestParam(name = "chucnang[]", required = false) List<Long> lstCnId ) {
		User _user = (User) session.getAttribute("USERLOGIN");
		if (_user != null) {
			User user = new User();
			long id = param.getLong("id", 0);
			long nhomId = param.getLong("nhomId", 0);
			String email = param.getString("email", "");
			
			// save user
			if(id > 0) {
				user = this.userService.getUser(id);
				user.setNguoiSua((String) session.getAttribute("USERNAME"));
				user.setNgaySua(new Date());
			}else {
				user.setStatus(0);
				user.setNgayTao(new Date());
				user.setNguoiTao((String) session.getAttribute("USERNAME"));
				String password = param.getString("password", "");
				String rePass = param.getString("rePass", "");
				String username = param.getString("username", "");
				if (password.equals(rePass) && !password.equals("")) {
					user.setUsername(username);
					user.setPassword(CommonUtil.getBcrypt(password));
				}else {
					return "redirect:/phongdaotao/user/addOrEdit";
				}
			}
			
			user.setDaXoa((byte)0);
			user.setEmail(email);
			user.setNhomid(nhomId);
			
			User u = this.userService.update(user);
			
			//save thong tin user
			String hoTen = param.getString("hoTen", "");
			int gioiTinh = param.getInt("gioiTinh", 0);
			String diaChi = param.getString("diaChi", "");
			String phone = param.getString("phone", "");
			String moTa = param.getString("moTa", "");
			
			Date birthday = new Date(0);
			JSONArray tableValue = new JSONArray();
			if (nhomId == 1 || nhomId == 2) {
				List<Long> lsCNlong = this.custom.getAllChucNangByNhomId(nhomId);
				if (lsCNlong != null && lsCNlong.size() > 0) {
					for (Long cnid : lsCNlong) {
						JSONObject json = new JSONObject();
						json.put("userid", u.getId());
						json.put("chucnangid", cnid);
						tableValue.put(json);
					}
				}
			}
			
			switch (Integer.parseInt(String.valueOf(nhomId))) {
			case 1:
				String ngaySinh = param.getString("ngaySinh", "");
				String phoneFamily = param.getString("phoneFamily", "");
				long lopId = param.getLong("lopId", 0);
				SinhVien sv = new SinhVien();
				
				if(id > 0) {
					sv = this.sinhVienService.getSinhVien(id);
					sv.setNguoiSua((String) session.getAttribute("USERNAME"));
					sv.setNgaySua(new Date());
				}else {
					sv.setId(u.getId());
					sv.setNgayTao(new Date());
					sv.setNguoiTao((String) session.getAttribute("USERNAME"));
				}
				
				if (!ngaySinh.equals("")) {
					birthday = CommonUtil.todate(ngaySinh, "yyyy-MM-dd");
					sv.setNgaySinh(birthday);
				}
				sv.setDaXoa((byte) 0);
				sv.setDiaChi(diaChi);
				sv.setGioiTinh(gioiTinh);
				sv.setHoTen(hoTen);
				sv.setMoTa(moTa);
				sv.setSoDienThoaiCaNhan(phone);
				sv.setSoDienThoaiGiaDinh(phoneFamily);
				
				if (lopId > 0) {
					LopHoc lop = this.lopHocService.getLopHoc(lopId);
					if (lop != null) {
						sv.setLop(lop);
					}
				}
				
				this.sinhVienService.update(sv);
				break;
			case 2:
				GiangVien gv = new GiangVien();
				if(id > 0) {
					gv = this.giangVienService.getGiangVien(id);
					gv.setNguoiSua((String) session.getAttribute("USERNAME"));
					gv.setNgaySua(new Date());
				}else {
					gv.setId(u.getId());
					gv.setNgayTao(new Date());
					gv.setNguoiTao((String) session.getAttribute("USERNAME"));
				}
				
				gv.setDaXoa((byte) 0);
				gv.setDiaChi(diaChi);
				gv.setGioiTinh(gioiTinh);
				gv.setHoTen(hoTen);
				gv.setMoTa(moTa);
				gv.setSoDienThoai(phone);
				
				this.giangVienService.update(gv);
				break;
			case 3:
				QuanTri qt = new QuanTri();
				if(id > 0) {
					qt = this.quanTriService.getQuanTri(id);
					qt.setNguoiSua((String) session.getAttribute("USERNAME"));
					qt.setNgaySua(new Date());
				}else {
					qt.setId(u.getId());
					qt.setNgayTao(new Date());
					qt.setNguoiTao((String) session.getAttribute("USERNAME"));
				}
				
				qt.setDaXoa((byte) 0);
				qt.setDiaChi(diaChi);
				qt.setGioiTinh(gioiTinh);
				qt.setHoTen(hoTen);
				qt.setSoDienThoai(phone);
				
				this.quanTriService.update(qt);
				
				if (lstCnId != null && lstCnId.size() > 0) {
					for (Long cnid : lstCnId) {
						JSONObject json = new JSONObject();
						json.put("userid", u.getId());
						json.put("chucnangid", cnid);
						tableValue.put(json);
					}
				}
				break;
			case 4:
				List<Long> lsCNlong = this.custom.getAllChucNangByNhomId(4);
				if (lsCNlong != null && lsCNlong.size() > 0) {
					for (Long chucNangId : lsCNlong) {
						JSONObject json = new JSONObject();
						json.put("userid", u.getId());
						json.put("chucnangid", chucNangId);
						tableValue.put(json);
					}
				}
				break;
			default:
				break;
			}
			
			//save chuc nang user
			this.custom.delete("qtht_user_chucnang", "userid", u.getId());
			if (tableValue.length() > 0) {
				List<String> tableCol = new ArrayList<>();
				tableCol.add("userid");
				tableCol.add("chucnangid");
				this.custom.insert("qtht_user_chucnang", tableCol, tableValue);
			}
			return "redirect:/phongdaotao/user/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/user/list")
	public String listUser() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				List<NhomNguoiDung> lsNhom = this.custom.getAllNhomUser("");
				session.setAttribute("lsNhom", lsNhom);
				return "user.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/user/loadmore")
	public String loadUser() {
		String ten = param.getString("ten", "");
		long nhomId = param.getLong("nhomId", 0);
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		
		List<UserDTO> lstUser = this.custom.getAllUser(nhomId, ten, count, delta);
		int totalCount = this.custom.getAllUserCount(nhomId, ten);
		
		param.setAttribute("listUser", lstUser);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "user.listitem";
		
	}
	
	@RequestMapping(value = "/user/addOrEdit")
	public String addOrEditUser(HttpSession session) {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long id = param.getLong("userId", 0);
				String title = "Thêm mới người dùng";
				long nhomId = param.getLong("nhomId", 1);
				UserDetailDTO u = new UserDetailDTO();
				u.setId(id);
				u.setNhomId(nhomId);
				
				User user = new User();
				if (id > 0) {
					user = this.userService.getUser(id);
					title = "Cập nhật người dùng";
					u.setEmail(user.getEmail());
				}
				
				if (nhomId == 1) {
					SinhVien sv = new SinhVien();
					if (id > 0) {
						sv = this.sinhVienService.getSinhVien(id);
						u.setDiaChi(sv.getDiaChi());
						u.setGioiTinh(sv.getGioiTinh());
						u.setHoTen(sv.getHoTen());
						u.setPhone(sv.getSoDienThoaiCaNhan());
					}
				}else if(nhomId == 2) {
					GiangVien gv = new GiangVien();
					if (id > 0) {
						gv = this.giangVienService.getGiangVien(id);
						u.setDiaChi(gv.getDiaChi());
						u.setGioiTinh(gv.getGioiTinh());
						u.setHoTen(gv.getHoTen());
						u.setPhone(gv.getSoDienThoai());
					}
				}else if(nhomId == 3) {
					QuanTri qt = new QuanTri();
					if (id > 0) {
						qt = this.quanTriService.getQuanTri(id);
						u.setDiaChi(qt.getDiaChi());
						u.setGioiTinh(qt.getGioiTinh());
						u.setHoTen(qt.getHoTen());
						u.setPhone(qt.getSoDienThoai());
					}
				}
				param.setAttribute("UserDetail", u);
				List<NhomNguoiDung> lsNhom = this.custom.getAllNhomUser("");
				session.setAttribute("lsNhom", lsNhom);
				session.setAttribute("title", title);
				
				return "user.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/user/delete")
	@ResponseBody
	public String deleteUser() {
		int kq = 0;
		long id = param.getLong("userId", 0);
		long nhomId = param.getLong("nhomId", 0);
		if (id > 0) {
			User user = this.userService.getUser(id);
			user.setDaXoa((byte) 1);
			this.userService.update(user);
			
			if (nhomId == 1) {
				SinhVien sv = this.sinhVienService.getSinhVien(id);
				sv.setDaXoa((byte) 1);
				this.sinhVienService.update(sv);
			}else if (nhomId == 2) {
				GiangVien qt = this.giangVienService.getGiangVien(id);
				qt.setDaXoa((byte) 1);
				this.giangVienService.update(qt);
			}else if (nhomId == 3) {
				QuanTri qt = this.quanTriService.getQuanTri(id);
				qt.setDaXoa((byte) 1);
				this.quanTriService.update(qt);
			}
			
			this.custom.delete("qtht_user_chucnang", "userid", id);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/user/getChiTiet")
	public String getChiTietUser(HttpSession session){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long id = param.getLong("userId", 0);
		long nhomId = param.getLong("nhomId", 1);
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("nhomId", nhomId);
		
		if (nhomId == 1) {
			String phoneFamily = "";
			String moTa = "";
			String ngaySinh = "";
			long lopId = 0;
			if (id > 0) {
				try {
					SinhVien sv = this.sinhVienService.getSinhVien(id);
					if (sv != null) {
						phoneFamily = sv.getSoDienThoaiGiaDinh();
						moTa = sv.getMoTa();
						lopId = sv.getLop().getId();
						ngaySinh = df.format(sv.getNgaySinh());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			json.put("lopId", lopId);
			json.put("moTa", moTa);
			json.put("ngaySinh", ngaySinh);
			json.put("phoneFamily", phoneFamily);
			
			List<LopHoc> lsLopHoc = this.custom.getAllLopHoc("", 0, -1, -1);
			session.setAttribute("lsLop", lsLopHoc);
		}else if(nhomId == 2) {
			String moTa = "";
			if (id> 0) {
				try {
					GiangVien gv = this.giangVienService.getGiangVien(id);
					if (gv != null) {
						moTa = gv.getMoTa();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.put("moTa", moTa);
		}else if(nhomId == 3) {
			List<ChucNang> lsChucNang = this.custom.findAllChucNang("", "", -1, -1);
			param.setAttribute("LISTCHUCNANG", lsChucNang);
		}
		param.setAttribute("Detail", json);
		return "user.detail";
	}
	
	@RequestMapping(value = "/user/checkma")
	@ResponseBody
	public String checkMaUser() {
		long id = param.getLong("userId", 0);
		String khoa = param.getString("khoa", "");
		List<User> ls = this.userService.findByUsername(khoa);
		boolean check = true;
		if (ls.size() > 0) {
			for (User user : ls) {
				if (user.getId() != id) {
					check = false;
					break;
				}
			}
		}
		
		return check ? "0" : "1";
	}
	
	@RequestMapping(value = "/user/xemChiTiet")
	public String xemChiTietUser() {
		long userId = param.getLong("userId", 0);
		long nhomId = param.getLong("nhomId", 1);
		JSONObject json = new JSONObject();
		
		String tenNhom = "Sinh viên";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		if (nhomId == 1) {
			tenNhom = "Sinh viên";
			String gioiTinh = "Nữ";
			SinhVien sv = this.sinhVienService.getSinhVien(userId);
			json.put("hoTen", sv.getHoTen());
			if (sv.getGioiTinh() == 1) {
				gioiTinh = "Nam";
			}else if (sv.getGioiTinh() == 2) {
				gioiTinh = "Khác";
			}
			json.put("gioiTinh", gioiTinh);
			json.put("ngaySinh", sv.getNgaySinh() != null ? df.format(sv.getNgaySinh()) : "");
			json.put("phone", sv.getSoDienThoaiCaNhan());
			json.put("phoneFamily", sv.getSoDienThoaiGiaDinh());
			json.put("diaChi", sv.getDiaChi());
			json.put("lop", sv.getLop() != null ? sv.getLop().getTen() : "");
			json.put("moTa", sv.getMoTa());
		}else if (nhomId == 2) {
			tenNhom = "Giảng viên";
			String gioiTinh = "Nữ";
			GiangVien gv = this.giangVienService.getGiangVien(userId);
			json.put("hoTen", gv.getHoTen());
			if (gv.getGioiTinh() == 1) {
				gioiTinh = "Nam";
			}else if (gv.getGioiTinh() == 2) {
				gioiTinh = "Khác";
			}
			json.put("gioiTinh", gioiTinh);
			json.put("phone", gv.getSoDienThoai());
			json.put("diaChi", gv.getDiaChi());
			json.put("moTa", gv.getMoTa());
		}else if (nhomId == 3) {
			tenNhom = "Phòng đào tạo";
			String gioiTinh = "Nữ";
			QuanTri qt = this.quanTriService.getQuanTri(userId);
			json.put("hoTen", qt.getHoTen());
			if (qt.getGioiTinh() == 1) {
				gioiTinh = "Nam";
			}else if (qt.getGioiTinh() == 2) {
				gioiTinh = "Khác";
			}
			json.put("gioiTinh", gioiTinh);
			json.put("phone", qt.getSoDienThoai());
			json.put("diaChi", qt.getDiaChi());
		}
		json.put("nhomId", nhomId);
		json.put("tenNhom", tenNhom);
		param.setAttribute("UserDetail", json);
		return "user.userdetail";
	}
	
	//quản lý môn học
	@PostMapping(value = "/monhoc/save")
	public String updateMon() {
		User _user = (User) session.getAttribute("USERLOGIN");
		if (_user != null) {
			MonHoc item = new MonHoc();
			long id = param.getLong("id", 0);
			String name = param.getString("ten", "");
			int soTiet = param.getInt("soTiet", 0);
			long giangVienId = param.getLong("giangVienId", 0);
			int soBuoi = 1 ;
			
			if(id > 0) {
				item = this.monHocService.getMonHoc(id);
				item.setNguoiSua((String) session.getAttribute("USERNAME"));
				item.setNgaySua(new Date());
			}else {
				item.setNgayTao(new Date());
				item.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			
			item.setDaXoa((byte)0);
			item.setTen(name);
			item.setSoTiet(soTiet);
			
			soBuoi = soTiet % 3 == 0 ? soTiet/3 : soTiet/3 + 1;
			item.setSoBuoi(soBuoi);
			
			if (giangVienId > 0) {
				GiangVien gv = this.giangVienService.getGiangVien(giangVienId);
				if (gv != null) {
					item.setGiangVien(gv);
				}
			}
			
			this.monHocService.update(item);
			
			return "redirect:/phongdaotao/monhoc/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/monhoc/list")
	public String listMon() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				return "monhoc.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/monhoc/loadmore")
	public String loadMon() {
		String name = param.getString("ten", "");
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		
		List<MonHocDTO> lstMon = this.custom.getAllMonHocDTO(name, count, delta);
		int totalCount = this.custom.getAllMonHocCount(name);
		
		param.setAttribute("listMonHoc", lstMon);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "monhoc.listitem";
		
	}
	
	@RequestMapping(value = "/monhoc/addOrEdit")
	public String addOrEditMonHoc() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long id = param.getLong("monId", 0);
				String title = "Thêm mới môn học";
				MonHoc item = new MonHoc();
				if (id > 0) {
					item = this.monHocService.getMonHoc(id);
					title = "Cập nhật môn học";
				}
				List<GiangVien> lsGiangViens = this.custom.getAllGiangVien();
				session.setAttribute("lsGiangVien", lsGiangViens);
				session.setAttribute("monHoc", item);
				session.setAttribute("title", title);
				
				return "monhoc.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/monhoc/delete")
	@ResponseBody
	public String deleteMonHoc() {
		int kq = 0;
		long id = param.getLong("monId", 0);
		if (id > 0) {
			MonHoc item = this.monHocService.getMonHoc(id);
			item.setDaXoa((byte) 1);
			this.monHocService.update(item);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	// thoi khoa bieu
	@RequestMapping(value = "/tkb/list")
	public String listThoiKhoaBieu() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long lopId = param.getLong("lopId", 0);
				param.setAttribute("lopId", lopId);
				return "tkb.list";
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
		param.setAttribute("lopId", lopId);
		
		return "tkb.listitem";
	}
	
	@PostMapping(value = "/tkb/delete")
	@ResponseBody
	public String deleteTKB() {
		int kq = 0;
		long id = param.getLong("tkbId", 0);
		if (id > 0) {
			ThoiKhoaBieu item = this.thoiKhoaBieuService.getThoiKhoaBieu(id);
			this.thoiKhoaBieuService.delete(item);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/tkb/addOrEdit")
	public String addOrEditTKB() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long id = param.getLong("tkbId", 0);
				long lopId = param.getLong("lopId", 0);
				
				List<PhongHoc> lsPhong  = this.custom.getAllPhongHoc("", 0, -1, -1);
				List<Object[]> lsMon = this.custom.getMonHocByLopId(lopId, 2);
				session.setAttribute("lsPhong", lsPhong);
				param.setAttribute("lopId", lopId);
				param.setAttribute("lsMon", lsMon);
				
				ThoiKhoaBieu item = new ThoiKhoaBieu();
				if (id > 0) {
					item = this.thoiKhoaBieuService.getThoiKhoaBieu(id);
					session.setAttribute("thoiKhoaBieu", item);
					return "tkb.edit";
				}else {
					return "tkb.add";
				}
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/tkb/update")
	public String updateTkb() {
		User _user = (User) session.getAttribute("USERLOGIN");
		if (_user != null) {
			ThoiKhoaBieu item = new ThoiKhoaBieu();
			long id = param.getLong("id", 0);
			long lopId = param.getLong("lopId", 0);
			
			if(id > 0) {
				int cahoc = param.getInt("cahoc", 0);
				long monId = param.getLong("monId", 0);
				long phongId = param.getLong("phongId", 0);
				String ngay = param.getString("ngay", "");
				String mota = param.getString("moTa", "");
				
				item = this.thoiKhoaBieuService.getThoiKhoaBieu(id);
				item.setCahoc(cahoc);
				item.setMota(mota);
				
				if (!ngay.equals("")) {
					Date date = CommonUtil.todate(ngay, "yyyy-MM-dd");
					item.setNgay(date);
				}
				
				if (lopId > 0) {
					LopHoc entity = this.lopHocService.getLopHoc(lopId);
					if (entity != null) {
						item.setLop(entity);
					}
				}
				
				if (monId > 0) {
					MonHoc entity = this.monHocService.getMonHoc(monId);
					if (entity != null) {
						item.setMon(entity);
					}
				}
				
				if (phongId > 0) {
					PhongHoc entity = this.phongHocService.getPhongHoc(phongId);
					if (entity != null) {
						item.setPhong(entity);
					}
				}
				
				this.thoiKhoaBieuService.update(item);
			}
			
			return "redirect:/phongdaotao/tkb/list?lopId=" + lopId;
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/tkb/create")
	public String addTKB(@RequestParam(name = "cahoc[]" , required = false) List<Integer> lsCaHoc
				,@RequestParam(name = "monId[]" , required = false) List<Long> lsMonHoc
				,@RequestParam(name = "phongId[]" , required = false, defaultValue = "0") List<Long> lsPhongHoc
				,@RequestParam(name = "ngay[]" , required = false) List<String> lsNgay
				,@RequestParam(name = "moTa[]" , required = false, defaultValue = "") List<String> lsMoTa) {
		User _user = (User) session.getAttribute("USERLOGIN");
		if (_user != null) {
			long lopId = param.getLong("lopId", 0);
			
			int size = lsNgay.size();
			for (int i = 0; i < size; i++) {
				ThoiKhoaBieu item = new ThoiKhoaBieu();
				item.setCahoc(lsCaHoc.get(i));
				item.setMota(lsMoTa.get(i));
				
				if (!lsNgay.get(i).equals("")) {
					Date date = CommonUtil.todate(lsNgay.get(i), "yyyy-MM-dd");
					item.setNgay(date);
				}
				
				if (lopId > 0) {
					LopHoc entity = this.lopHocService.getLopHoc(lopId);
					if (entity != null) {
						item.setLop(entity);
					}
				}
				
				if (lsMonHoc.get(i) > 0) {
					MonHoc entity = this.monHocService.getMonHoc(lsMonHoc.get(i));
					if (entity != null) {
						item.setMon(entity);
					}
				}
				
				if (lsPhongHoc.get(i) > 0) {
					PhongHoc entity = this.phongHocService.getPhongHoc(lsPhongHoc.get(i));
					if (entity != null) {
						item.setPhong(entity);
					}
				}
				
				this.thoiKhoaBieuService.update(item);
			}
			
			//send thong bao
			Map<String, Object> map = new HashMap<>();
			map.put("lopId", lopId);
			map.put("loai", 1);
			map.put("nguoiTao", CommonUtil.getTenNguoiDungByUserId(_user.getId(), 3));
			map.put("user", _user);
			CommonUtil.sendThongBao(map);
			return "redirect:/phongdaotao/tkb/list?lopId=" + lopId;
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lop/viewDiemThi")
	public String viewDiemThi() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			long lopId = param.getLong("lopId", 0);
			long monId = param.getLong("monId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("Lop", this.lopService.getLopHoc(lopId));
			param.setAttribute("monId", monId);
			return "gv.lop.viewdiemthi";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/lop/viewDiemDanh")
	public String viewDiemDanh() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			long lopId = param.getLong("lopId", 0);
			long monId = param.getLong("monId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			List<Object[]> lsTkb = this.custom.getLichDayByLopIdAndMonId(-1, lopId, monId);
			
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("lsTKB", lsTkb);
			param.setAttribute("Lop", this.lopService.getLopHoc(lopId));
			return "gv.lop.viewdiemdanh";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/phepSV")
	public String listPhepSV() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			return "gv.phep.list";
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/lophoc/updateWT")
	@ResponseBody
	public String updateWT() {
		User _u = (User) session.getAttribute("USERLOGIN");
		int kq = 0;
		long lopId = param.getLong("lopId", 0);
		long monId = param.getLong("monId", 0);
		int loai = param.getInt("loai", 0);
		if (lopId > 0 && monId > 0) {
			if (loai == 1) {
				this.custom.updateWorkTask(lopId, monId);
				//send thong bao
				Map<String, Object> map = new HashMap<>();
				map.put("lopId", lopId);
				map.put("loai", 4);
				map.put("nguoiTao", CommonUtil.getTenNguoiDungByUserId(_u.getId(), 3));
				map.put("user", _u);
				map.put("mon", this.monHocService.getMonHoc(monId).getTen());
				CommonUtil.sendThongBao(map);
			}
			if(loai == 2) {
				this.custom.updateDanhGia(lopId, monId);
				//send thong bao
				Map<String, Object> map = new HashMap<>();
				map.put("lopId", lopId);
				map.put("loai", 5);
				map.put("nguoiTao", CommonUtil.getTenNguoiDungByUserId(_u.getId(), 3));
				map.put("user", _u);
				map.put("mon", this.monHocService.getMonHoc(monId).getTen());
				CommonUtil.sendThongBao(map);
			}
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/lop/viewNhatKy")
	public String viewNhatKy() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long lopId = param.getLong("lopId", 0);
				List<ThoiKhoaBieuDTO> lsData =  this.custom.getAllThoiKhoaBieuByGiangVienId(-1,lopId, "", CommonUtil.toString(new Date(), "yyyy-MM-dd"));
				List<CoSo> lsCoSo = this.coSoService.findAll();
				
				session.setAttribute("lsCoSo", lsCoSo);
				param.setAttribute("lsTKB", lsData);
				param.setAttribute("Lop", this.lopService.getLopHoc(lopId));
				return "gv.lop.viewnhatky";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
}
