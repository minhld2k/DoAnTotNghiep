package com.doan.totnghiep.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.entities.MonHoc;
import com.doan.totnghiep.entities.SinhVienWorkTask;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.entities.WorkTask;
import com.doan.totnghiep.entities.WorkTaskDetail;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.MonHocService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.WorkTaskDetailService;
import com.doan.totnghiep.services.WorkTaskService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
@RequestMapping("/worktask")
public class WorkTaskController {
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CustomRepository custom;
	
	@Autowired
	WorkTaskService workTaskService;
	
	@Autowired
	MonHocService monService;
	
	@Autowired
	WorkTaskDetailService workTaskDetailService;
	
	@Autowired
	SinhVienService svService;
	
	@RequestMapping(value = "/list")
	public String listKhoaHoc() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			String _url = param.getServletPath();
			if (CommonUtil.checkQuyen(_url, u)) {
				//data
				long monId = param.getLong("monId", 0);
				param.setAttribute("lsWorkTask", this.custom.getAllWorkTask(monId));
				param.setAttribute("monId", monId);
				return "wt.list";
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
				long id = param.getLong("wtId", 0);
				long monId = param.getLong("monId", 0);
				String title = "Thêm mới work/task";
				WorkTask item = null;
				if (id > 0) {
					item = this.workTaskService.getWorkTask(id);
					title = "Cập nhật work/task";
				}
				session.setAttribute("title", title);
				param.setAttribute("workTask", item);
				param.setAttribute("monId", monId);
				
				return "wt.edit";
			}else {
				return "redirect:/403";
			}
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/save")
	public String updateLopHoc(@RequestParam(name = "tenWTDetail[]" , required = false)List<String> lsTenWTDetail) {
		User _user = (User) session.getAttribute("USERLOGIN");
		if (_user != null) {
			WorkTask wt = new WorkTask();
			long id = param.getLong("id", 0);
			String name = param.getString("tenWorkTask", "");
			int thuTu = param.getInt("thuTu", 0);
			long monId = param.getLong("monId", 0);
			
			if(id > 0) {
				wt = this.workTaskService.getWorkTask(id);
				wt.setNguoiSua((String) session.getAttribute("USERNAME"));
				wt.setNgaySua(new Date());
			}else {
				wt.setNgayTao(new Date());
				wt.setNguoiTao((String) session.getAttribute("USERNAME"));
				wt.setDaXoa((byte)0);
				if (monId > 0) {
					MonHoc mon = this.monService.getMonHoc(monId);
					if (mon != null) {
						wt.setMon(mon);
					}
				}
				
			}
			
			wt.setTen(name);
			wt.setThuTu(thuTu);		
			this.workTaskService.save(wt);
			
			//save work/task detail
			if (id > 0) {
				//xóa work/task cũ
				List<WorkTaskDetail> lsDetail = this.custom.getWorkTaskDetailByWTId(id);
				if (lsDetail != null && lsDetail.size() > 0) {
					for (WorkTaskDetail workTaskDetail : lsDetail) {
						this.workTaskDetailService.delete(workTaskDetail);
					}
				}
			}
			
			//add mới work/task detail
			for (int i = 0; i < lsTenWTDetail.size(); i++) {
				WorkTaskDetail wtd = new WorkTaskDetail();
				wtd.setDaXoa((byte) 0);
				wtd.setNgayTao(new Date());
				wtd.setNguoiTao((String) session.getAttribute("USERNAME"));
				wtd.setThuTu(i + 1);
				wtd.setTen(lsTenWTDetail.get(i));
				wtd.setWorkTask(wt);
				
				this.workTaskDetailService.save(wtd);
			}
			
			return "redirect:/worktask/list?monId="+ monId;
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/delete")
	@ResponseBody
	public String deleteKhoaHoc() {
		int kq = 0;
		long id = param.getLong("wtId", 0);
		if (id > 0) {
			WorkTask wt = this.workTaskService.getWorkTask(id);
			wt.setDaXoa((byte) 1);
			this.workTaskService.save(wt);
			
			//delete work/task detail
			List<WorkTaskDetail> lsDetail = this.custom.getWorkTaskDetailByWTId(id);
			if (lsDetail != null && lsDetail.size() > 0) {
				for (WorkTaskDetail workTaskDetail : lsDetail) {
					workTaskDetail.setDaXoa((byte) 1);
					this.workTaskDetailService.save(workTaskDetail);
				}
			}
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
			param.setAttribute("lsWTSV", lsData);
			param.setAttribute("monId", monId);
			return "wt.viewsv";
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping(value = "/saveWTSV")
	@ResponseBody
	public String saveWTSV(@RequestParam(name = "wtId[]",required = false)List<Long> lsWtId,@RequestParam(name = "ketQua[]",defaultValue = "0") List<Integer> lsKetQua
			,@RequestParam(name = "yKienKhac[]" ,defaultValue = "null")List<String> lsYKien) {
		int kq = 0;
		User u = (User) session.getAttribute("USERLOGIN");
		try {
			for (int i = 0; i < lsWtId.size(); i++) {
				SinhVienWorkTask svwt = new SinhVienWorkTask();
				svwt.setKetQua(lsKetQua.get(i));
				svwt.setNgayTao(new Date());
				svwt.setSinhVienId(u.getId());
				svwt.setWorkTaskDetailId(lsWtId.get(i));
				svwt.setYKien(lsYKien.get(i).equals("null") ? "" : lsYKien.get(i));
				
				this.custom.updateSinhVienWorkTask(svwt);
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
	public ResponseEntity<List<SinhVienWorkTask>> getDiemThiSV() {
		int loai = param.getInt("loai", 0);
		User u = (User) session.getAttribute("USERLOGIN");
		long monId = param.getLong("monId", 0);
		long userId = u.getId();
		if (loai == 1) {
			userId = param.getLong("sinhVienId", 0);
		}
		List<SinhVienWorkTask> lsData = this.custom.getSinhVienWTBySinhVienId(userId,monId);
		return new ResponseEntity<>(lsData,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/viewPDT")
	public String viewPDT() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			long monId = param.getLong("monId", 0);
			long lopId = param.getLong("lopId", 0);
			
			List<Object[]> lsSinhVien = this.custom.getAllSinhVienByLopId(lopId);
			List<WorkTask> lsData = this.custom.getAllWorkTask(monId);
			param.setAttribute("lsSinhVien", lsSinhVien);
			param.setAttribute("lsWTSV", lsData);
			param.setAttribute("monId", monId);
			return "wt.viewpdt";
		}else {
			return "redirect:/login";
		}
	}

}
