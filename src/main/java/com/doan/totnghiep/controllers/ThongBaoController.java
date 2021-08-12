package com.doan.totnghiep.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doan.totnghiep.dto.ThongBaoDTO;
import com.doan.totnghiep.entities.ThongBao;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.ThongBaoService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
public class ThongBaoController {
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ThongBaoService thongBaoService;
	
	@Autowired
	CustomRepository custom;
	
	@RequestMapping(value = "/thongbao/listNhan")
	public String listThongBaoNhan() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			return "tb.listNhan";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/thongbao/listgui")
	public String listThongBaoGui() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				return "tb.listGui";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/thongbao/load")
	public String loadGui() {
		User _u = (User) session.getAttribute("USERLOGIN");
		List<ThongBaoDTO> lsData = new ArrayList<ThongBaoDTO>();
		int totalCount = 0;
		
		int count = param.getInt("count", 0);
		int delta = count > 0 ? 10 : 20;
		int loaiDs = param.getInt("loaiDS", 0);
		String title = param.getString("title", "");
		switch (loaiDs) {
		case 1:
			lsData = this.custom.getAllThongBao(_u.getId(), title, count, delta);
			totalCount = this.custom.getCountThongBao(_u.getId(), title);
			break;
		case 2:
			int status = param.getInt("status", 0);
			lsData = this.custom.getThongBaoByUserId(_u.getId(), status,title, count, delta);
			totalCount = this.custom.getCountThongBaoByUserId(_u.getId(), status,title);
			break;

		default:
			break;
		}
		
		param.setAttribute("loaiDS", loaiDs);
		param.setAttribute("listThongBao", lsData);
		param.setAttribute("count", count);
		param.setAttribute("totalCount", totalCount);
		return "tb.lisitem";
	}
	
	@RequestMapping("/thongbao/addOrEdit")
	public String viewFormTB() {
		int truongHopId = param.getInt("truongHopId", 0);
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				String title = "Thêm mới thông báo";
				ThongBao tb = new ThongBao();
				
				param.setAttribute("truongHopId", truongHopId);
				session.setAttribute("thongBao", tb);
				session.setAttribute("title", title);
				
				return "tb.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/thongbao/save")
	public String saveThongBao(@RequestParam(name = "userId[]", required = false) List<Long> listUserId
				,@RequestParam(name = "khoaId[]", required = false) List<Long> listKhoaId
				,@RequestParam(name = "lopId[]", required = false) List<Long> listLopId) {
		User u = (User) session.getAttribute("USERLOGIN");
		String tieuDe = param.getString("tieuDe", "");
		String noiDung = param.getString("noiDung", "");
		
		ThongBao tb = new ThongBao();
		tb.setNgaySua(new Date());
		tb.setNgayTao(new Date());
		tb.setNguoiTao((String) session.getAttribute("USERNAME"));
		tb.setDaXoa((byte) 0);
		tb.setLoai(0);
		tb.setUser(u);
		
		tb.setTieuDe(tieuDe);
		tb.setNoiDung(noiDung);
		
		this.thongBaoService.update(tb);
		
		if (listUserId != null && listUserId.size() > 0) {
			CommonUtil.addThongBaoUser(listUserId, tb.getId());
		}
		
		if (listKhoaId != null && listKhoaId.size() > 0) {
			for (Long long1 : listKhoaId) {
				List<Long> lsUserIdByKhoa = this.custom.getAllUserIdByKhoaId(long1);
				CommonUtil.addThongBaoUser(lsUserIdByKhoa, tb.getId());
			}
		}
		
		if (listLopId != null && listLopId.size() > 0) {
			for (Long long1 : listLopId) {
				List<Long> lsUserIdByLop = this.custom.getAllUserIdByLopId(long1);
				CommonUtil.addThongBaoUser(lsUserIdByLop, tb.getId());
			}
		}
		
		return "redirect:/thongbao/listgui";
	}
	
	@RequestMapping(value = "/thongbao/xemChiTiet")
	public String xemChiTietThongBao() {
		User u = (User) session.getAttribute("USERLOGIN");
		long tbId = param.getLong("thongBaoId", 0);
		int loaiDS = param.getInt("loaiDS", 0);
		if (tbId > 0) {
			ThongBao tb = this.thongBaoService.getThongBao(tbId);
			if (loaiDS == 2) {
				this.custom.upDateTrangThaiThongBao(tbId, u.getId());
			}
			
			param.setAttribute("loaiDS", loaiDS);
			param.setAttribute("ThongBaoDetail", tb);
			return "tb.detail";
		}
		return "";
	}
	
	@RequestMapping(value = "/thongbao/loadHome")
	public String loadHome() {
		return "tb.loadhome";
	}
}
