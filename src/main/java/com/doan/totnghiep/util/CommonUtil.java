package com.doan.totnghiep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.doan.totnghiep.dto.Menu;
import com.doan.totnghiep.dto.ThongBaoDTO;
import com.doan.totnghiep.dto.UserDTO;
import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.entities.Phep;
import com.doan.totnghiep.entities.QuanTri;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.entities.ThongBao;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.entities.WorkTaskDetail;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.GiangVienService;
import com.doan.totnghiep.services.LopHocService;
import com.doan.totnghiep.services.QuanTriService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.ThongBaoService;
import com.doan.totnghiep.services.UserService;

public class CommonUtil {
	
	public static SinhVienService sinhVienService = SpringUtils.getBean(SinhVienService.class);
	public static GiangVienService giangVienService = SpringUtils.getBean(GiangVienService.class);
	public static QuanTriService pdtService = SpringUtils.getBean(QuanTriService.class);
	public static CustomRepository custom = SpringUtils.getBean(CustomRepository.class);
	public static ThongBaoService tbService = SpringUtils.getBean(ThongBaoService.class);
	public static UserService userService = SpringUtils.getBean(UserService.class);
	public static LopHocService lopService = SpringUtils.getBean(LopHocService.class);
	
	public static String getBcrypt(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
	
	public static boolean checkBcrypt(String password1,String password2){
		return BCrypt.checkpw(password1, password2);
	}
	
	static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
    public static String toString(Date date,String...pa){
        if (pa.length > 0) {
            date_format.applyPattern(pa[0]);
        }
        if (null == date) {
			return null;
		}
        return date_format.format(date);
    }
    public static Date todate(String date,String...pa){
        try {
        	
            if (pa.length > 0) {
                date_format.applyPattern(pa[0]);
            }
            if (null == date || "".equals(date)) {
				return null;
			}else {
				date = date.replace("/", "-");
			}
            return date_format.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static SinhVien getSinhVienByUserId(long userId) {
    	if (userId > 0) {
    		SinhVien sv = sinhVienService.getSinhVien(userId);
    		if (sv != null) {
    			return sv;
    		}
		}
    	
    	return null;
    }
    
    public static GiangVien getGiangVienByUserId(long userId) {
    	if (userId > 0) {
    		GiangVien gv = giangVienService.getGiangVien(userId);
    		if (gv != null) {
    			return gv;
    		}
		}
    	
    	return null;
    }
    
    public static QuanTri getPDTByUserId(long userId) {
    	if (userId > 0) {
    		QuanTri gv = pdtService.getQuanTri(userId);
    		if (gv != null) {
    			return gv;
    		}
		}
    	
    	return null;
    }
    
    public static String pageHome(User user) {
    	if (user.getNhomid() == 1) {
			return "sv.home";
		}else if(user.getNhomid() == 2) {
			return "gv.home";
		}else if(user.getNhomid() == 3){
			return "pdt.home";
		}else {
			return "admin.home";
		}
    }
    
    public static boolean checkQuyen(String url, User user) {
    	boolean kq = false;
    	if (user != null && !url.isEmpty()) {
			kq = custom.getChucNangByUrlAndUser(url, user.getId()) > 0;
		}
    	return kq;
    }
    
    public static ThongBao getThongBaoById(long tbId) {
    	return tbService.getThongBao(tbId);
    }
    
    public static String getTenNguoiDungByUserId(long userId, long nhomId) {
    	String ten = "";
    	if (nhomId == 1) {
			ten = getSinhVienByUserId(userId).getHoTen();
		}else if(nhomId == 2) {
			ten = getGiangVienByUserId(userId).getHoTen();
		}else if (nhomId == 3) {
			ten = getPDTByUserId(userId).getHoTen();
		}else {
			ten = "admin";
		}
    	return ten;
    }
    
    public static List<UserDTO> getAllUser(long nhomId){
    	return custom.getAllUser(nhomId, "", -1, -1);
    }
    
    public static List<KhoaHoc> getAllKhoaHoc(){
    	return custom.getAllKhoaHoc("", 0, 0, -1, -1);
    }
    
    public static List<LopHoc> getAllLopHoc(long khoaId,User user){
    	if (user != null && user.getNhomid() == 2) {
    		List<Object[]> lsData =  custom.getAllLopByGiangVienId(user.getId());
    		List<LopHoc> result = new ArrayList<LopHoc>();
    		if (lsData != null && lsData.size() > 0) {
				for(int i = 0; i<lsData.size();i++) {
					result.add(lopService.getLopHoc((long) lsData.get(i)[0]));
				}
			}
    		return result;
		}else {
			return custom.getAllLopHoc("", khoaId, -1, -1);
		}
    }
    
    public static void addThongBaoUser(List<Long> listUserId, long tbId) {
    	List<String> tableCol = new ArrayList<>();
		tableCol.add("thongbaoid");
		tableCol.add("nguoinhanid");
		
		JSONArray tableValue = new JSONArray();
		for (Long cnid : listUserId) {
			JSONObject json = new JSONObject();
			json.put("thongbaoid", tbId);
			json.put("nguoinhanid", cnid);
			tableValue.put(json);
		}
		
		custom.delete("qtht_user_thongbao", "thongbaoid", tbId);
		custom.insert("qtht_user_thongbao", tableCol, tableValue);
    }
    
    public static JSONArray getNguoiDungByThongBaoId(long thongBaoId) {
    	JSONArray arr = new JSONArray();
    	List<Long>  listUser = custom.getAllUserIdByThongBaoId(thongBaoId);
    	if (listUser != null && listUser.size() > 0) {
    		for (Long long1 : listUser) {
    			User u = userService.getUser(long1);
    			JSONObject json = new JSONObject();
    			json.put("id", long1);
    			json.put("ten", getTenNguoiDungByUserId(long1, u.getNhomid()));
    			arr.put(json);
    		}
		}
    	
    	return arr;
    }
    
    public static Map<String, Object> getThongBaoHome(long userId){
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<ThongBaoDTO> listThongBao = custom.getThongBaoByUserId(userId, 0, "", 0, 5);
    	int totalCount = custom.getCountThongBaoByUserId(userId, 0, "");
    	map.put("lsData", listThongBao);
    	map.put("totalCount", totalCount);
    	return map;
    }
    
    public static String getTime(Date date) {
    	Date current = new Date();
		String timeStr = "";
		long diff = current.getTime() - date.getTime();
		long time = diff / 1000;
		if(time >= 60){
			time = diff / (60 * 1000);
			if(time >= 60){
				time = diff/ (60 * 60 * 1000);
				if(time >= 24){
					time = diff / (24*60*60*1000);
					if(time >= 30){
						time = diff / (30*24*60*60*1000);
						if(time >= 12){
							time = diff / (12 * 30 * 24 * 60 *60 * 1000);
							timeStr = time + " năm trước";
						}else{
							timeStr = time + " tháng trước";
						}
					}else{
						timeStr = time + " ngày trước";
					}
				}else{
					timeStr = time + " giờ trước";
				}
			}else{
				timeStr = time + " phút trước";
			}
		}else{
			timeStr = time + " giây trước";
		}
		
		return timeStr;
    }
    
    public static List<Menu> getMenuByUserId(long userId, int module){
    	return custom.getMenuByUserId(userId, module);
    }
    
    public static boolean checkQuyenByKhoa(long userId, String khoa) {
    	return custom.checkQuyenByKhoa(userId, khoa) > 0;
    }
    
    public static List<WorkTaskDetail> getWTDetailByWTid(long wtId){
    	return custom.getWorkTaskDetailByWTId(wtId);
    }
    
    public static int getMaxThuTuOfWT(long monId) {
    	return custom.getMaxThuTuOfWT(monId) + 1;
    }
    
    public static int getMaxThuTuOfDanhGia(long monId) {
    	return custom.getMaxThuTuOfDanhGia(monId) + 1;
    }
    
    public static int select(String colum, long lopId, long monId) {
    	return custom.select(colum, lopId, monId);
    }
    
    public static Map<String, String> getGioByCa(ThoiKhoaBieu tkb){
    	Map<String, String> json = new HashMap<>();
    	switch (tkb.getCahoc()) {
		case 1:
			json.put("gioVao", "08:00");
			json.put("gioBatDau", "08:00");
			json.put("gioRa", "11:00");
			break;
		case 2:
			json.put("gioVao", "14:00");
			json.put("gioBatDau", "14:00");
			json.put("gioRa", "17:00");
			break;
		case 3:
			json.put("gioVao", "18:00");
			json.put("gioBatDau", "18:00");
			json.put("gioRa", "21:00");
			break;
		default:
			break;
		}
    	return json;
    }
    
    public static void sendThongBao(Map<String, Object> map) {
    	int loai = (int) map.get("loai");
    	ThongBao tb = new ThongBao();
    	tb.setNgaySua(new Date());
		tb.setNgayTao(new Date());
		tb.setDaXoa((byte) 0);
		tb.setNguoiTao((String)map.get("nguoiTao"));
		tb.setUser((User) map.get("user"));
		
		long lopId = (long) map.get("lopId");
		List<Long> lsSVid = custom.getAllUserIdByLopId(lopId);
    	switch (loai) {
		case 1:
			//thông báo tkb
			tb.setLoai(1);
			tb.setTieuDe("THÔNG BÁO LỊCH DẠY VÀ HỌC");
			tb.setNoiDung("Thông báo lịch dạy và học tuần mới");
			
			tbService.update(tb);
			
			//send SV,GV
			List<Long> lsGiangVienId = custom.getGiangVienByLopId(lopId);
			for (Long long1 : lsSVid) {
				lsGiangVienId.add(long1);
			}
			CommonUtil.addThongBaoUser(lsGiangVienId, tb.getId());
			break;
		case 2:
			//thông báo nghĩ phép
			Phep phep = (Phep) map.get("phep");
			tb.setLoai(2);
			tb.setTieuDe("XIN NGHỈ PHÉP");
			tb.setNoiDung("Sinh viên : " + (String)map.get("nguoiTao") + " xin phép nghỉ học từ ngày " + CommonUtil.toString(phep.getTuNgay(), "dd/MM/yyyy") 
				+ " đến ngày " +CommonUtil.toString(phep.getDenNgay(), "dd/MM/yyyy")  +" với lý do: " + phep.getLyDo());
			tbService.update(tb);
			
			//send GV,PDT
			List<Long> lsGiangVien = custom.getGiangVienByLopId(lopId);
			List<UserDTO> lsUser = custom.getAllUser(3, "", -1, -1);
			for (UserDTO _u : lsUser) {
				lsGiangVien.add(_u.getId());
			}
			CommonUtil.addThongBaoUser(lsGiangVien, tb.getId());
			break;
		case 3:
			//thông báo diem thi
			tb.setLoai(3);
			tb.setTieuDe("THÔNG BÁO ĐIỂM THI");
			tb.setNoiDung("Môn " + (String) map.get("mon") + " đã có điểm thi");
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		case 4:
			//thông báo đánh giá work/task
			tb.setLoai(4);
			tb.setTieuDe("THÔNG BÁO ĐÁNH GIÁ WORK/TASK");
			tb.setNoiDung("Phòng đào tạo thông báo sinh vào đánh giá work/task môn " + (String) map.get("mon"));
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		case 5:
			//thông báo tự đánh giá
			tb.setLoai(5);
			tb.setTieuDe("THÔNG BÁO TỰ ĐÁNH GIÁ");
			tb.setNoiDung("Phòng đào tạo thông báo sinh vào tự đánh giá môn " + (String) map.get("mon"));
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		default:
			break;
		}
    }
}
