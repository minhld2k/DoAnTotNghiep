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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.dto.DiemDanhDTO;
import com.doan.totnghiep.dto.PhongHocDTO;
import com.doan.totnghiep.dto.ThoiKhoaBieuDTO;
import com.doan.totnghiep.entities.CoSo;
import com.doan.totnghiep.entities.DiemDanh;
import com.doan.totnghiep.entities.DiemThi;
import com.doan.totnghiep.entities.NhatKy;
import com.doan.totnghiep.entities.PhongHoc;
import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.entities.WorkTask;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.CoSoService;
import com.doan.totnghiep.services.DiemDanhService;
import com.doan.totnghiep.services.DiemThiService;
import com.doan.totnghiep.services.GiangVienService;
import com.doan.totnghiep.services.LopHocService;
import com.doan.totnghiep.services.MonHocService;
import com.doan.totnghiep.services.NhatKyService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.PhongHocService;
import com.doan.totnghiep.services.ThoiKhoaBieuService;
import com.doan.totnghiep.services.UserService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping(value = "/giangvien")
public class GiangVienController {
	
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	GiangVienService giangVienService;
	
	@Autowired
	DiemDanhService diemDanhService;
	
	@Autowired
	DiemThiService diemThiService;
	
	@Autowired
	ThoiKhoaBieuService tkbService;
	
	@Autowired
	NhatKyService nhatKyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CoSoService coSoService;
	
	@Autowired
	PhongHocService phongService;
	
	@Autowired
	LopHocService lopService;
	
	@Autowired
	MonHocService monService;
	
	@Autowired
	CustomRepository custom;
	
	@RequestMapping(value = "/lop/viewDiemDanh")
	public String viewDiemDanh() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long lopId = param.getLong("lopId", 0);
				long monId = param.getLong("monId", 0);
				
				List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
				List<Object[]> lsTkb = this.custom.getLichDayByLopIdAndMonId(_u.getId(), lopId, monId);
				
				param.setAttribute("lsSinhVien", lsSinhVien);
				param.setAttribute("lsTKB", lsTkb);
				param.setAttribute("Lop", this.lopService.getLopHoc(lopId));
				return "gv.lop.viewdiemdanh";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/lop")
	public String viewLop() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				List<Object[]> lsData = this.custom.getAllLopByGiangVienId(_u.getId());
				param.setAttribute("LopGV", lsData);
				return "gv.lop.list";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping(value = "/lop/getMonHoc", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId() {
		Map<String, String> map = new HashMap<String, String>();
		long lopId = param.getLong("lopId", 0);
		User _u = (User) session.getAttribute("USERLOGIN");
		List<Object[]> lsMon = this.custom.getAllMonHocByLopAndGiangVien(_u.getId(), lopId);
		if (lsMon != null && lsMon.size() > 0) {
			for (int i = 0; i < lsMon.size(); i++) {
				map.put(lsMon.get(i)[0].toString(), lsMon.get(i)[1].toString());
			}
			map.put("size", String.valueOf(lsMon.size()));
		}
		return map;
	}
	
	@PostMapping(value = "/lop/saveDiemDanh")
	public String saveDiemDanh(@RequestParam(name = "sinhVienId[]", required = false) List<Long> lsSinhVienId
				,@RequestParam(name = "diemDanh[]", required = false) List<Integer> lsDiemDanh) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long tkbId = param.getLong("tkbId", 0);
			ThoiKhoaBieu tkb = this.tkbService.getThoiKhoaBieu(tkbId);
			this.custom.delete("qlsv_diemdanhs", "tkbid", tkbId);
			for (int i = 0; i < lsSinhVienId.size(); i++) {
				int kienThuc = param.getInt(lsSinhVienId.get(i)+"_kienThuc", 0);
				int thucHanh = param.getInt(lsSinhVienId.get(i)+"_thucHanh", 0);
				DiemDanh dd = new DiemDanh();
				dd.setDaXoa((byte)0);
				dd.setNgayTao(new Date());
				dd.setNguoiTao((String) session.getAttribute("USERNAME"));
				dd.setDenLop(lsDiemDanh.get(i));
				dd.setGhiChu("");
				dd.setKienThuc(kienThuc);
				dd.setThucHanh(thucHanh);
				dd.setSinhVien(this.userService.getUser(lsSinhVienId.get(i)));
				dd.setTkb(tkb);
				
				this.diemDanhService.add(dd);
			}
			
			if(u.getNhomid() == 2) {
				return "redirect:/giangvien/lop";
			}else {
				return "redirect:/phongdaotao/lophoc/list";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping(value = "/lop/loadDataSV", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<DiemDanhDTO>> getSinhVienByTkb() {
		long tkbId = param.getLong("tkbId", 0);
		List<DiemDanhDTO> lsData = this.custom.getDiemDanhByTkbId(tkbId);
		return new ResponseEntity<>(lsData,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lop/viewDiemThi")
	public String viewDiemThi() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long lopId = param.getLong("lopId", 0);
				long monId = param.getLong("monId", 0);
				
				List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
				
				param.setAttribute("lsSinhVien", lsSinhVien);
				param.setAttribute("Lop", this.lopService.getLopHoc(lopId));
				param.setAttribute("monId", monId);
				return "gv.lop.viewdiemthi";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/lop/saveDiemThi")
	public String saveDiemThi(@RequestParam(name = "sinhVienId[]", required = false) List<Long> lsSinhVienId
				,@RequestParam(name = "lyThuyet[]", defaultValue = "0") List<String> lsLyThuyet
				,@RequestParam(name = "thucHanh[]", defaultValue = "0") List<String> lsThucHanh
				,@RequestParam(name = "ghiChu[]", defaultValue = "null") List<String> lsGhiChu) {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long monId = param.getLong("monId", 0);
			long lopId = param.getLong("lopId", 0);
			long lopMonId = this.custom.getLopMonId(lopId, monId);
			for (int i = 0; i < lsSinhVienId.size(); i++) {
				DiemThi dt = new DiemThi();
				dt.setGhiChu(lsGhiChu.get(i).equals("null") ? "" : lsGhiChu.get(i));
				dt.setLopMonId(lopMonId);
				dt.setLyThuyet(lsLyThuyet.get(i).equals("0") ? "" : lsLyThuyet.get(i));
				dt.setThucHanh(lsThucHanh.get(i).equals("0") ? "" : lsThucHanh.get(i));
				dt.setSinhVienId(lsSinhVienId.get(i));
				dt.setNguoiTao((String)session.getAttribute("USERNAME"));
				dt.setNgayTao(new Date());
				
				this.diemThiService.add(dt);
			}
			//send thong bao
			Map<String, Object> map = new HashMap<>();
			map.put("lopId", lopId);
			map.put("loai", 3);
			map.put("nguoiTao", CommonUtil.getTenNguoiDungByUserId(u.getId(), 2));
			map.put("user", u);
			map.put("mon", this.monService.getMonHoc(monId).getTen());
			CommonUtil.sendThongBao(map);
			
			//Ket thuc mon
			this.custom.updateTrangThaiMonHoc(lopMonId,2);
			
			return "redirect:/giangvien/lop";
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping(value = "/lop/loadDiemThiSV", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<DiemThi>> getDiemThiSV() {
		long monId = param.getLong("monId", 0);
		long lopId = param.getLong("lopId", 0);
		long lopMonId = this.custom.getLopMonId(lopId, monId);
		List<DiemThi> lsData = this.custom.getDiemThiSV(lopMonId);
		return new ResponseEntity<>(lsData,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tkb")
	public String lichDay() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			return "gv.tkb.list";
		}else {
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value = "/tkb/load")
	public String loadTKB() {
		User _u = (User) session.getAttribute("USERLOGIN");
		String tuNgay = param.getString("tuNgay", "");
		String denNgay = param.getString("denNgay", "");
		
		List<ThoiKhoaBieuDTO> lsData =  this.custom.getAllThoiKhoaBieuByGiangVienId(_u.getId(),-1, tuNgay, denNgay);
		param.setAttribute("TKB", lsData);
		
		return "gv.tkb.listitem";
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
	
	@RequestMapping(value = "/phepSV/load")
	public String loadPhep() {
		User _u = (User) session.getAttribute("USERLOGIN");
		String tuNgay = param.getString("tuNgay", "");
		long userId = _u.getId();
		if (_u.getNhomid() == 3 || _u.getNhomid() == 4) {
			userId = 0;
		}
		List<Object[]> lsData = this.custom.getAllPhepSV(userId, tuNgay);
		
		param.setAttribute("lsPhep", lsData);
		return "gv.phep.listitem";
	}
	
	@RequestMapping(value = "/lop/viewNhatKy")
	public String viewNhatKy() {
		User _u = (User) session.getAttribute("USERLOGIN");
		if (_u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, _u)) {
				long lopId = param.getLong("lopId", 0);
				List<ThoiKhoaBieuDTO> lsData =  this.custom.getAllThoiKhoaBieuByGiangVienId(_u.getId(),lopId, "", CommonUtil.toString(new Date(), "yyyy-MM-dd"));
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
	
	@PostMapping(value = "/lop/saveNhatKy")
	public String saveNhatKy() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long tkbId = param.getLong("tkbId", 0);
			int buoiThu = param.getInt("buoiThu", 0);
			long wtId = param.getLong("wtId", 0);
			String loiNhanPDT = param.getString("loiNhanPDT", "");
			long phongId = param.getLong("phongHocId", 0);
			String gioVao = param.getString("gioVao", "");
			String gioBatDau = param.getString("gioBatDau", "");
			int danhGiaVao = param.getInt("danhGiaVao", 0);
			int lyDoVao = param.getInt("lyDoVao", 0);
			String gioRa = param.getString("gioRa", "");
			int danhGiaRa = param.getInt("danhGiaRa", 0);
			int lyDoRa = param.getInt("lyDoRa", 0);
			int siSo = param.getInt("siSo", 0);
			int hieuBai = param.getInt("hieuBai", 0);
			int koHieu = param.getInt("koHieu", 0);
			String danhGiaGV = param.getString("danhGiaGV", "");
			String loiNhanGV = param.getString("loiNhanGV", "");
			String ghiChu = param.getString("ghiChu", "");
			
			NhatKy nk = this.custom.getNhatKyByTkbId(tkbId);
			if (nk != null) {
				nk.setNgaySua(new Date());
				nk.setNguoiSua((String) session.getAttribute("USERNAME"));
			}else {
				nk = new NhatKy();
				nk.setNgayTao(new Date());
				nk.setNguoiTao((String) session.getAttribute("USERNAME"));
				nk.setDaXoa((byte) 0);
			}
			nk.setBuoiThu(buoiThu);
			nk.setDanhGiaGv(danhGiaGV);
			nk.setDanhGiaRa(danhGiaRa);
			nk.setDanhGiaVao(danhGiaVao);
			nk.setGhiChu(ghiChu);
			nk.setGioBatDau(gioBatDau);
			nk.setGioRa(gioRa);
			nk.setGioVao(gioVao);
			nk.setHieuBai(hieuBai);
			nk.setKohieu(koHieu);
			nk.setLoiNhanGv(loiNhanGV);
			nk.setLoiNhanPdt(loiNhanPDT);
			nk.setLyDoRa(lyDoRa);
			nk.setLyDoVao(lyDoVao);
			nk.setPhongId(phongId);
			nk.setSiSo(siSo);
			nk.setTkbId(tkbId);
			nk.setWorkTaskId(wtId);
			
			this.nhatKyService.add(nk);
			
			if(u.getNhomid() == 2) {
				return "redirect:/giangvien/lop";
			}else {
				return "redirect:/phongdaotao/lophoc/list";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping(value = "/getPhongHoc", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<PhongHoc>> getPhongHoc() {
		long coSoId = param.getLong("coSoId", 0);
		List<PhongHoc> lsData = this.custom.getAllPhongHoc("", coSoId, -1,-1);
		return new ResponseEntity<>(lsData,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lop/loadDataNhatKy", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getNhatKy() {
		long tkbId = param.getLong("tkbId", 0);
		ThoiKhoaBieu tkb = this.tkbService.getThoiKhoaBieu(tkbId);
		List<WorkTask> lsWT = this.custom.getAllWorkTask(tkb.getMon().getId());
		NhatKy nk = this.custom.getNhatKyByTkbId(tkbId);
		PhongHocDTO phong = null;
		long coSoId = 0;
		if (tkb.getPhong() != null) {
			phong = this.custom.getPhongHocById(tkb.getPhong().getId());
		}
		if (nk != null) {
			coSoId = this.custom.getPhongHocById(nk.getPhongId()).getCosoid();
		}
		
		Map<String, String> json = CommonUtil.getGioByCa(tkb);
		
		int siSo = 0;
		try {
			siSo = this.custom.getAllSinhVienByLopId(tkb.getLop().getId()).size();
		} catch (Exception e) {
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lsWT", lsWT);
		map.put("Phong", phong);
		map.put("nhatKy", nk);
		map.put("coSoId", coSoId);
		map.put("buoiThu", this.custom.getBuoiThuInNhatKy(tkb) + 1);
		map.put("lsGio", json);
		map.put("siSo", siSo);
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
}
