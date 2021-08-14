package com.doan.totnghiep.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.entities.QuanTri;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.services.GiangVienService;
import com.doan.totnghiep.services.ParamService;
import com.doan.totnghiep.services.QuanTriService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.UserService;
import com.doan.totnghiep.util.CommonUtil;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@Autowired
	SinhVienService sinhVienService;
	
	@Autowired
	GiangVienService giangVienService;
	
	@Autowired
	QuanTriService quanTriService;
	
	@Autowired
	ParamService param;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/")
	public String home() {
		User u = (User) session.getAttribute("USERLOGIN");
		if (u != null) {
			if (u.getStatus() > 0) {
				//return page home
				return CommonUtil.pageHome(u);
			}else {
				if (u.getNhomid() >=1 && u.getNhomid() <= 3 ) {
					//return page cap nhat thong tin
					return "home.userProfile";
				}else {
					return "admin.home";
				}
			}
		}else {	
			session.removeAttribute("MESSAGE");
			return "home.login";
		}
	}
	
	@GetMapping(value = "/login")
	public String viewLogin() {
		return "home.login";
	}
	
	@PostMapping("/checkLogin")
	public String checkLogin() {
		String username = param.getString("username", "");
		String password = param.getString("password", "");
		if (this.userService.checkLogin(username, password)) {
			User user = this.userService.findByUsername(username).get(0);
			session.setAttribute("USERNAME", username);
			session.setAttribute("USERLOGIN", user);
			session.removeAttribute("ERROR");
			if (user.getStatus() > 0) {
				//return page home
				return "redirect:/";
			}else {
				if (user.getNhomid() >=1 && user.getNhomid() <= 3 ) {
					//return page cap nhat thong tin
					return "redirect:/updateUserProfile";
				}else {
					return "redirect:/";
				}
			}
		} else {
			session.setAttribute("MESSAGE", "Tài khoản hoặc mật khẩu không chính xác");
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.removeAttribute("MESSAGE");
		session.removeAttribute("USERNAME");
		session.removeAttribute("USERLOGIN");
		return "redirect:/login";
	}
	
	@GetMapping(value = "/updateUserProfile")
	public String viewUserProfile() {
		return "home.userProfile";
	}
	
	@GetMapping(value = "/viewProfile")
	public String viewUserDetail() {
		return "home.userDetail";
	}
	
	@PostMapping(value = "/saveUserProfile")
	public String saveUserProfile() {
		User user = (User) session.getAttribute("USERLOGIN");
		String hoTen = param.getString("hoTen", "");
		int gioiTinh = param.getInt("gioiTinh", 0);
		String diaChi = param.getString("diaChi", "");
		String phone = param.getString("phone", "");
		String moTa = param.getString("moTa", "");
		Date birthday = new Date(0);
		
		switch (Integer.parseInt(String.valueOf(user.getNhomid()))) {
		case 1:
			String ngaySinh = param.getString("ngaySinh", "");
			String phoneFamily = param.getString("phoneFamily", "");
			SinhVien sv = this.sinhVienService.getSinhVien(user.getId());
			sv.setNguoiSua((String) session.getAttribute("USERNAME"));
			sv.setNgaySua(new Date());
			
			if (!ngaySinh.equals("")) {
				birthday = CommonUtil.todate(ngaySinh, "yyyy-MM-dd");
				sv.setNgaySinh(birthday);
			}
			sv.setDiaChi(diaChi);
			sv.setGioiTinh(gioiTinh);
			sv.setHoTen(hoTen);
			sv.setMoTa(moTa);
			sv.setSoDienThoaiCaNhan(phone);
			sv.setSoDienThoaiGiaDinh(phoneFamily);
			
			this.sinhVienService.update(sv);
			break;
		case 2:
			GiangVien gv = this.giangVienService.getGiangVien(user.getId());
			gv.setNguoiSua((String) session.getAttribute("USERNAME"));
			gv.setNgaySua(new Date());
			
			gv.setDiaChi(diaChi);
			gv.setGioiTinh(gioiTinh);
			gv.setHoTen(hoTen);
			gv.setMoTa(moTa);
			gv.setSoDienThoai(phone);
			
			this.giangVienService.update(gv);
			break;
		case 3:
			QuanTri qt = this.quanTriService.getQuanTri(user.getId());
			qt.setNguoiSua((String) session.getAttribute("USERNAME"));
			qt.setNgaySua(new Date());
			
			qt.setDiaChi(diaChi);
			qt.setGioiTinh(gioiTinh);
			qt.setHoTen(hoTen);
			qt.setSoDienThoai(phone);
			
			this.quanTriService.update(qt);
			
			break;
		default:
			break;
		}
		
		String passOld = param.getString("passwordOld", "");
		String passNew = param.getString("passwordNew", "");
		String rePass = param.getString("rePass", "");
		String email = param.getString("email", "");
		user.setEmail(email);
		boolean setPass = false;
		if (!passOld.isEmpty() && CommonUtil.checkBcrypt(passOld, user.getPassword())) {
			if (!passNew.isEmpty() && passNew.equals(rePass)) {
				user.setPassword(CommonUtil.getBcrypt(passNew));
				setPass = true;
			}
		}
		this.userService.update(user);
		
		if (setPass) {
			return "redirect:/logout";
		}else {
			return "redirect:/viewProfile";
		}
	}
	
	@PostMapping(value = "/updateUserProfile")
	public String updateProfile() {
		User user = (User) session.getAttribute("USERLOGIN");
		String hoTen = param.getString("hoTen", "");
		int gioiTinh = param.getInt("gioiTinh", 0);
		String diaChi = param.getString("diaChi", "");
		String phone = param.getString("phone", "");
		String moTa = param.getString("moTa", "");
		Date birthday = new Date(0);
		
		switch (Integer.parseInt(String.valueOf(user.getNhomid()))) {
		case 1:
			String ngaySinh = param.getString("ngaySinh", "");
			String phoneFamily = param.getString("phoneFamily", "");
			SinhVien sv = this.sinhVienService.getSinhVien(user.getId());
			sv.setNguoiSua((String) session.getAttribute("USERNAME"));
			sv.setNgaySua(new Date());
			
			if (!ngaySinh.equals("")) {
				birthday = CommonUtil.todate(ngaySinh, "yyyy-MM-dd");
				sv.setNgaySinh(birthday);
			}
			sv.setDiaChi(diaChi);
			sv.setGioiTinh(gioiTinh);
			sv.setHoTen(hoTen);
			sv.setMoTa(moTa);
			sv.setSoDienThoaiCaNhan(phone);
			sv.setSoDienThoaiGiaDinh(phoneFamily);
			
			this.sinhVienService.update(sv);
			break;
		case 2:
			GiangVien gv = this.giangVienService.getGiangVien(user.getId());
			gv.setNguoiSua((String) session.getAttribute("USERNAME"));
			gv.setNgaySua(new Date());
			
			gv.setDiaChi(diaChi);
			gv.setGioiTinh(gioiTinh);
			gv.setHoTen(hoTen);
			gv.setMoTa(moTa);
			gv.setSoDienThoai(phone);
			
			this.giangVienService.update(gv);
			break;
		case 3:
			QuanTri qt = this.quanTriService.getQuanTri(user.getId());
			qt.setNguoiSua((String) session.getAttribute("USERNAME"));
			qt.setNgaySua(new Date());
			
			qt.setDiaChi(diaChi);
			qt.setGioiTinh(gioiTinh);
			qt.setHoTen(hoTen);
			qt.setSoDienThoai(phone);
			
			this.quanTriService.update(qt);
			
			break;
		default:
			break;
		}
		
		String password = param.getString("password", "");
		String rePass = param.getString("rePass", "");
		String email = param.getString("email", "");
		if (!password.isEmpty() && password.equals(rePass)) {
			user.setEmail(email);
			user.setPassword(CommonUtil.getBcrypt(password));
			user.setStatus(1);
			
			this.userService.update(user);
		}
		
		return CommonUtil.pageHome(user);
		
	}
	
	@GetMapping("/403")
	public String accessDenied() {
		return "home.denied";
	}
	
	@RequestMapping("/checkPass")
	@ResponseBody
	public String checkPass() {
		User _user = (User) session.getAttribute("USERLOGIN");
		String password = param.getString("password", "");
		int kq = 0;
		if((!password.equals("") && CommonUtil.checkBcrypt(password, _user.getPassword())) || password.equals("")) {
			kq = 1;
		}
		return String.valueOf(kq);
	}
	
	@GetMapping("/quenMatKhau")
	public String viewFormQMK() {
		return "home.qmk";
	}
	
	@RequestMapping("/formXacNhan")
	public String viewFormXacNhan() {
		return "home.xacnhan";
	}
	
	@PostMapping("/quenMatKhau")
	public String viewNext() {
		String username = param.getString("username", "");
		if (!username.equals("")) {
			try {
				User u = this.userService.findByUsername(username).get(0);
				if (u != null) {
					String email = u.getEmail();
					String code = CommonUtil.sendMail(email);
					session.setAttribute("EMAIL", email);
					session.setAttribute("Code", code);
					session.setAttribute("username", username);
					session.removeAttribute("ERROR_USERNAME");
					return "redirect:/formXacNhan";
				}
			} catch (Exception e) {
				session.setAttribute("ERROR_USERNAME", "Tên đăng nhập không tồn tại");
				return "redirect:/quenMatKhau";
			}
		}else {
			session.setAttribute("ERROR_USERNAME", "Tên đăng nhập không tồn tại");
			return "redirect:/quenMatKhau";
		}
		return "";
	}
	
	@GetMapping("/viewChangPass")
	public String viewChangPass() {
		return "home.changepass";
	}
	
	@PostMapping("/changePass")
	public String changPass() {
		String username = (String) session.getAttribute("username");
		String password = param.getString("passwordNew", "");
		String rePass = param.getString("rePassNew", "");
		
		if (!username.equals("")) {
			try {
				User u = this.userService.findByUsername(username).get(0);
				if (u != null && password.equals(rePass) && !password.equals("")) {
					u.setPassword(CommonUtil.getBcrypt(password));
					this.userService.update(u);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return "redirect:/login";
	}
}
