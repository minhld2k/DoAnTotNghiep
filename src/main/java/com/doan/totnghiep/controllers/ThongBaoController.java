package com.doan.totnghiep.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.doan.totnghiep.dto.ThongBaoDTO;
import com.doan.totnghiep.entities.ThongBao;
import com.doan.totnghiep.entities.UniFileUpLoads;
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
				String title = "Th??m m???i th??ng b??o";
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
				,@RequestParam(name = "lopId[]", required = false) List<Long> listLopId
				,@RequestParam(name = "tepDinhKem", required = false) MultipartFile file) {
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
		
		//luu file
		if(!file.isEmpty()) {
			CommonUtil.uploadFile(file, 0, tb.getId(), (String) session.getAttribute("USERNAME"));
		}
		
		return "redirect:/thongbao/listgui";
	}
	
	@RequestMapping(value = "/thongbao/xemChiTiet")
	public String xemChiTietThongBao() {
		long tbId = param.getLong("thongBaoId", 0);
		int loaiDS = param.getInt("loaiDS", 0);
		if (tbId > 0) {
			ThongBao tb = this.thongBaoService.getThongBao(tbId);
			
			param.setAttribute("loaiDS", loaiDS);
			param.setAttribute("ThongBaoDetail", tb);
			return "tb.detail";
		}
		return "";
	}
	
	@GetMapping(value = "/thongbao/setDaXem")
	@ResponseBody
	public String setDaXem() {
		int kq = 0;
		User u = (User) session.getAttribute("USERLOGIN");
		long tbId = param.getLong("thongBaoId", 0);
		if (tbId > 0) {
			this.custom.upDateTrangThaiThongBao(tbId, u.getId());
			kq = 1;
		}else {
			List<ThongBaoDTO> listThongBao = this.custom.getThongBaoByUserId(u.getId(), 0, "", -1, -1);
			for (ThongBaoDTO thongBaoDTO : listThongBao) {
				this.custom.upDateTrangThaiThongBao(thongBaoDTO.getId(), u.getId());
			}
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@RequestMapping(value = "/thongbao/loadHome")
	public String loadHome() {
		return "tb.loadhome";
	}
	
	@RequestMapping(value = "/download")
	@ResponseBody
	public ResponseEntity<byte[]> downloadFileCong(){
		String maSo = param.getString("maSo", "");
		String folderUpload = "/uploads";
		if (!maSo.isEmpty()) {
			try {
				folderUpload += File.separator + maSo;
				UniFileUpLoads fileUpLoads = this.custom.getUniFileUploadByMaSo(maSo);
				File file = new File(folderUpload);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUpLoads.getTenFile() + "\"")
						.contentType(MediaType.valueOf(fileUpLoads.getKieuFile()))
						.body(Files.readAllBytes(Paths.get(file.getPath())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
