package com.doan.totnghiep.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.entities.ChucNang;
import com.doan.totnghiep.entities.NhomNguoiDung;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.ChucNangService;
import com.doan.totnghiep.services.NhomNguoiDungService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping("/quantri")
public class AdminController {
	@Autowired
	HttpSession session;
	
	@Autowired
	ParamService param;
	
	@Autowired
	CustomRepository custom;
	
	@Autowired
	ChucNangService chucNangService;
	
	@Autowired
	NhomNguoiDungService nhomService;
	
	// chuc nang
	@PostMapping(value = "/chucnang/save")
	public String updateChucNang() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			ChucNang chucNang = new ChucNang();
			long id = param.getLong("id", 0);
			String url = param.getString("url", "");
			String icon = param.getString("icon", "");
			String name = param.getString("name", "");
			String key = param.getString("key", "");
			Boolean isMeNu = param.getBoolean("ismenu", false);
			int module = param.getInt("module", 1);
			
			if(id > 0) {
				chucNang = this.chucNangService.getChucNang(id);
				chucNang.setNguoiSua((String) session.getAttribute("USERNAME"));
				chucNang.setNgaySua(new Date());
			}else {
				chucNang.setNgayTao(new Date());
				chucNang.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			chucNang.setDaXoa((byte)0);
			chucNang.setUrl(url);
			chucNang.setIcon(icon);
			chucNang.setIsmenu(isMeNu ? 1:0);
			chucNang.setName(name);
			chucNang.setKey(key);
			chucNang.setModule(module);
			
			this.chucNangService.update(chucNang);
			return "redirect:/quantri/chucnang/list";
		}else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value = "/chucnang/list")
	public String listChucNang(HttpSession session) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				return "chucnang.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/chucnang/loadmore")
	public String loadChucNang() {
		String tenChucNang = param.getString("tenChucNang", "");
		String khoa = param.getString("khoa", "");
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		List<ChucNang> lstChucNang = this.custom.findAllChucNang(tenChucNang, khoa, count, delta);
		int totalCount = this.custom.findAllChucNangCount(tenChucNang, khoa);
		
		param.setAttribute("tenChucNang", tenChucNang);
		param.setAttribute("khoa", khoa);
		param.setAttribute("listChucNang", lstChucNang);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		
		return "chucnang.listitem";
		
	}
	
	@RequestMapping(value = "/chucnang/addOrEdit")
	public String addOrEditChucNang(HttpSession session) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("chucNangId", 0);
				String title = "Thêm mới chức năng";
				ChucNang chucNang = new ChucNang();
				if (id > 0) {
					chucNang = this.chucNangService.getChucNang(id);
					title = "Cập nhật chức năng";
				}
				session.setAttribute("chucNang", chucNang);
				session.setAttribute("title", title);
				return "chucnang.editChucNang";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/chucnang/delete")
	@ResponseBody
	public String deleteChucNang() {
		int kq = 0;
		long id = param.getLong("chucNangId", 0);
		if (id > 0) {
			ChucNang cn = this.chucNangService.getChucNang(id);
			cn.setDaXoa((byte) 1);
			this.chucNangService.update(cn);
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/chucnang/checkma")
	@ResponseBody
	public String checkMaChucNang() {
		long id = param.getLong("chucNangId", 0);
		String khoa = param.getString("khoa", "");
		List<ChucNang> ls = this.chucNangService.findByKey(khoa);
		boolean check = true;
		if (ls.size() > 0) {
			for (ChucNang chucNang : ls) {
				if (chucNang.getId() != id) {
					check = false;
					break;
				}
			}
		}
		
		return check ? "0" : "1";
	}
	
	//nhom nguoi dung
	@PostMapping(value = "/nhom/save")
	public String updateNhom(HttpSession session, @RequestParam(name = "chucnang[]", required = false) List<Long> lstCnId) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			NhomNguoiDung nhom = new NhomNguoiDung();
			long id = param.getLong("id", 0);
			String name = param.getString("tenNhom", "");
			
			if(id > 0) {
				nhom = this.nhomService.getNhomNguoiDung(id);
				nhom.setNguoiSua((String) session.getAttribute("USERNAME"));
				nhom.setNgaySua(new Date());
			}else {
				nhom.setNgayTao(new Date());
				nhom.setNguoiTao((String) session.getAttribute("USERNAME"));
			}
			nhom.setDaXoa((byte)0);
			nhom.setTen(name);
			
			this.nhomService.update(nhom);
			
			if (lstCnId != null && lstCnId.size() > 0) {
				List<String> tableCol = new ArrayList<>();
				tableCol.add("nhomid");
				tableCol.add("chucnangid");
				
				JSONArray tableValue = new JSONArray();
				for (Long cnid : lstCnId) {
					JSONObject json = new JSONObject();
					json.put("nhomid", nhom.getId());
					json.put("chucnangid", cnid);
					tableValue.put(json);
				}
				
				this.custom.delete("qtht_nhom_chucnang", "nhomid", nhom.getId());
				this.custom.insert("qtht_nhom_chucnang", tableCol, tableValue);
			}
			
			return "redirect:/quantri/nhom/list";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/nhom/list")
	public String listNhom(HttpSession session) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				return "nhomnguoidung.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/nhom/loadmore")
	public String loadNhom() {
		String tenNhom = param.getString("tenNhom", "");
		List<NhomNguoiDung> lstNhomNguoiDung = this.custom.getAllNhomUser(tenNhom);
		int totalCount = this.custom.getAllNhomUserCount(tenNhom);
		
		param.setAttribute("tenNhom", tenNhom);
		param.setAttribute("lstNhomNguoiDung", lstNhomNguoiDung);
		param.setAttribute("totalCount", totalCount);
		
		return "nhomnguoidung.listitem";
		
	}
	
	@RequestMapping(value = "/nhom/addOrEdit")
	public String addOrEditNhom(HttpSession session) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				long id = param.getLong("nhomId", 0);
				String title = "Thêm mới nhóm người dùng";
				NhomNguoiDung nhom = new NhomNguoiDung();
				if (id > 0) {
					nhom = this.nhomService.getNhomNguoiDung(id);
					title = "Cập nhật nhóm người dùng";
				}
				session.setAttribute("nhomNguoiDung", nhom);
				session.setAttribute("title", title);
				
				param.setAttribute("LISTCHUCNANG", this.custom.findAllChucNang("", "", -1, -1));
				return "nhomnguoidung.editNhom";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/nhom/delete")
	@ResponseBody
	public String deleteNhom() {
		int kq = 0;
		long id = param.getLong("nhomId", 0);
		if (id > 0) {
			NhomNguoiDung nhom = this.nhomService.getNhomNguoiDung(id);
			nhom.setDaXoa((byte) 1);
			this.nhomService.update(nhom);
			this.custom.delete("qtht_nhom_chucnang", "nhomid", nhom.getId());
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@GetMapping(value = "/nhom/getChucNang", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId() {
		Map<String, String> map = new HashMap<String, String>();
		long nhomId = param.getLong("nhomId", 0);
		long userId = param.getLong("userId", 0);
		List<Long> lsCn = new ArrayList<>();
		if (userId > 0) {
			lsCn = this.custom.getAllChucNangByUserId(userId);
		}else {
			if (nhomId > 0) {
				lsCn = this.custom.getAllChucNangByNhomId(nhomId);
			}
		}
		map.put("chucnangs", lsCn.toString());

		return map;
	}
	
}
