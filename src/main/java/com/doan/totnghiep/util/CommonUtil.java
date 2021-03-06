package com.doan.totnghiep.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import com.doan.totnghiep.dto.Menu;
import com.doan.totnghiep.dto.ThongBaoDTO;
import com.doan.totnghiep.dto.UserDTO;
import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.entities.NgoaiHeThong;
import com.doan.totnghiep.entities.Phep;
import com.doan.totnghiep.entities.QuanTri;
import com.doan.totnghiep.entities.SinhVien;
import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.entities.ThongBao;
import com.doan.totnghiep.entities.UniFileUpLoads;
import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.entities.WorkTaskDetail;
import com.doan.totnghiep.repositories.CustomRepository;
import com.doan.totnghiep.services.GiangVienService;
import com.doan.totnghiep.services.LopHocService;
import com.doan.totnghiep.services.QuanTriService;
import com.doan.totnghiep.services.SinhVienService;
import com.doan.totnghiep.services.ThongBaoService;
import com.doan.totnghiep.services.UniFileUpLoadsService;
import com.doan.totnghiep.services.UserService;

public class CommonUtil {
	
	public static SinhVienService sinhVienService = SpringUtils.getBean(SinhVienService.class);
	public static GiangVienService giangVienService = SpringUtils.getBean(GiangVienService.class);
	public static QuanTriService pdtService = SpringUtils.getBean(QuanTriService.class);
	public static CustomRepository custom = SpringUtils.getBean(CustomRepository.class);
	public static ThongBaoService tbService = SpringUtils.getBean(ThongBaoService.class);
	public static UserService userService = SpringUtils.getBean(UserService.class);
	public static LopHocService lopService = SpringUtils.getBean(LopHocService.class);
	public static UniFileUpLoadsService uploadService = SpringUtils.getBean(UniFileUpLoadsService.class);
	public static JavaMailSender javaMailSender = SpringUtils.getBean(JavaMailSender.class);
	
	private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
	
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
							timeStr = time + " n??m tr?????c";
						}else{
							timeStr = time + " th??ng tr?????c";
						}
					}else{
						timeStr = time + " ng??y tr?????c";
					}
				}else{
					timeStr = time + " gi??? tr?????c";
				}
			}else{
				timeStr = time + " ph??t tr?????c";
			}
		}else{
			timeStr = time + " gi??y tr?????c";
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
			//th??ng b??o tkb
			tb.setLoai(1);
			tb.setTieuDe("TH??NG B??O L???CH D???Y V?? H???C");
			tb.setNoiDung("Th??ng b??o l???ch d???y v?? h???c tu???n m???i");
			
			tbService.update(tb);
			
			//send SV,GV
			List<Long> lsGiangVienId = custom.getGiangVienByLopId(lopId);
			for (Long long1 : lsSVid) {
				lsGiangVienId.add(long1);
			}
			CommonUtil.addThongBaoUser(lsGiangVienId, tb.getId());
			break;
		case 2:
			//th??ng b??o ngh?? ph??p
			Phep phep = (Phep) map.get("phep");
			tb.setLoai(2);
			tb.setTieuDe("XIN NGH??? PH??P");
			tb.setNoiDung("Sinh vi??n : " + (String)map.get("nguoiTao") + " xin ph??p ngh??? h???c t??? ng??y " + CommonUtil.toString(phep.getTuNgay(), "dd/MM/yyyy") 
				+ " ?????n ng??y " +CommonUtil.toString(phep.getDenNgay(), "dd/MM/yyyy")  +" v???i l?? do: " + phep.getLyDo());
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
			//th??ng b??o diem thi
			tb.setLoai(3);
			tb.setTieuDe("TH??NG B??O ??I???M THI");
			tb.setNoiDung("M??n " + (String) map.get("mon") + " ???? c?? ??i???m thi");
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		case 4:
			//th??ng b??o ????nh gi?? work/task
			tb.setLoai(4);
			tb.setTieuDe("TH??NG B??O ????NH GI?? WORK/TASK");
			tb.setNoiDung("Ph??ng ????o t???o th??ng b??o sinh v??o ????nh gi?? work/task m??n " + (String) map.get("mon"));
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		case 5:
			//th??ng b??o t??? ????nh gi??
			tb.setLoai(5);
			tb.setTieuDe("TH??NG B??O T??? ????NH GI??");
			tb.setNoiDung("Ph??ng ????o t???o th??ng b??o sinh v??o t??? ????nh gi?? m??n " + (String) map.get("mon"));
			tbService.update(tb);
			
			//send SV
			CommonUtil.addThongBaoUser(lsSVid, tb.getId());
			break;
		default:
			break;
		}
    }
    
    public static String SafeStringHTML(String str) {
        if (str == null) return new String();
        String temp = str;
        temp = temp.replaceAll("\'", "&#039;");
        temp = temp.replaceAll("\"", "&quot;");
        temp = temp.replaceAll("<", "&lt;");
        temp = temp.replaceAll(">", "&gt;");
        temp = temp.replaceAll("[\\\\]", "\\\\\\\\");
        return temp;
    }
    
    public static UniFileUpLoads uploadFile(MultipartFile fileUpload, int kieu, long doiTuongId, String nguoiTao) {
    	return uploadService.upload(fileUpload, kieu, doiTuongId, nguoiTao);
    }
    
    public static List<UniFileUpLoads> getUniFileUpLoads(long doiTuongId, int kieu){
    	return custom.getUniFileUpLoads(doiTuongId, kieu);
    }
    
    private static Random generator = new Random();
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
    
    
    public static String sendMail(String email) {
    	String code = randomAlphaNumeric(6);
    	try {
    		SimpleMailMessage msg = new SimpleMailMessage();
    		msg.setTo(email);
    		msg.setSubject("H??? th???ng Qu???n l?? sinh vi??n tr?????ng Ispace - qu??n m???t kh???u");
    		msg.setText("K??nh g???i: Qu?? ??ng/B??, \r\n "
    				+ "M?? x??c nh???n: "+ code + "\r\n"
    				+" Th??ng b??o n??y ???????c g???i t??? ?????ng t??? h??? th???ng Qu???n l?? sinh vi??n, vui l??ng kh??ng ph???n h???i l???i email n??y.\r\n"
    				+ "Tr??n tr???ng!");
    		javaMailSender.send(msg);
        	return code;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
	}
    
    public static String getXepLoai(double dtb) {
    	String xepLoai = "";
    	if(dtb < 5 ) {
    		xepLoai = "Kh??ng ?????t";
    	}else if((5 <= dtb) && (dtb < 7)) {
    		xepLoai = "Trung b??nh";
    	}else if((5 <= dtb) && (dtb < 7)) {
    		xepLoai = "Trung b??nh kh??";
    	}else if((7 <= dtb) && (dtb < 8)) {
    		xepLoai = "Kh??";
    	}else if((8 <= dtb) && (dtb < 9)) {
    		xepLoai = "Gi???i";
    	}else if((9 <= dtb) && (dtb <= 10)) {
    		xepLoai = "Xu???t s???c";
    	}
    	return xepLoai;
    }
    
    public static NgoaiHeThong getDiemBySinhVienId(long sinhVienId) {
    	return custom.getDiemBySinhVienId(sinhVienId);
    }
    
    public static double getDiemTBMon(String lyThuyet,String thucHanh) {
    	if (lyThuyet.equals("")) {
			return Double.parseDouble(thucHanh);
		}else if (thucHanh.equals("")) {
			return Double.parseDouble(lyThuyet);
		}else {
			return (Double.parseDouble(lyThuyet) + Double.parseDouble(thucHanh)*2)/3 ;
		}
    }
    
    public static double getDiemTBHocKy(List<Object[]> lsDiem) {
    	int soTiet = 0;
    	double dtb = 0;
    	for (int i = 0; i < lsDiem.size(); i++) {
    		int tiet = Integer.parseInt(lsDiem.get(i)[1].toString());
			dtb += getDiemTBMon(lsDiem.get(i)[2].toString(), lsDiem.get(i)[3].toString()) * tiet;
			soTiet += tiet;
		}
    	
    	return dtb/soTiet;
    }
}
