package com.doan.totnghiep.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.doan.totnghiep.dto.DiemDanhDTO;
import com.doan.totnghiep.dto.LopHocDTO;
import com.doan.totnghiep.dto.Menu;
import com.doan.totnghiep.dto.MonHocDTO;
import com.doan.totnghiep.dto.PhepDTO;
import com.doan.totnghiep.dto.PhongHocDTO;
import com.doan.totnghiep.dto.ThoiKhoaBieuDTO;
import com.doan.totnghiep.dto.ThongBaoDTO;
import com.doan.totnghiep.dto.UserDTO;
import com.doan.totnghiep.entities.ChucNang;
import com.doan.totnghiep.entities.CoSo;
import com.doan.totnghiep.entities.DanhGia;
import com.doan.totnghiep.entities.DiemThi;
import com.doan.totnghiep.entities.GiangVien;
import com.doan.totnghiep.entities.KhoaHoc;
import com.doan.totnghiep.entities.LopHoc;
import com.doan.totnghiep.entities.NgoaiHeThong;
import com.doan.totnghiep.entities.NhatKy;
import com.doan.totnghiep.entities.NhomNguoiDung;
import com.doan.totnghiep.entities.PhongHoc;
import com.doan.totnghiep.entities.SinhVienDanhGia;
import com.doan.totnghiep.entities.SinhVienWorkTask;
import com.doan.totnghiep.entities.ThoiKhoaBieu;
import com.doan.totnghiep.entities.UniFileUpLoads;
import com.doan.totnghiep.entities.WorkTask;
import com.doan.totnghiep.entities.WorkTaskDetail;
import com.doan.totnghiep.util.CommonUtil;

@Repository
public class CustomRepository {
	@Autowired
	public JdbcTemplate _jdbcTemplate;
	
	@Autowired
	public KhoaHocRepository khoaHocRepository;
	
	@Autowired
	public CoSoRepository coSoRepository;
	
	public List<ChucNang> findAllChucNang(String tenChucNang, String khoa,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT * FROM qtht_chucnangs ")
			.append(" 	WHERE daxoa = 0 ");
		if (!khoa.equals("")) {
			sql.append(" 	AND key ilike '%"+khoa+"%' ");
		}
		if (!tenChucNang.equals("")) {
			sql.append(" 	AND name ilike '%"+tenChucNang+"%' ");
		}
		
		sql.append(" 	ORDER BY module ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<ChucNang> lscn =  _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ChucNang>(ChucNang.class));
		return lscn;
	}
	
	public int findAllChucNangCount(String tenChucNang, String khoa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT COUNT(*) FROM qtht_chucnangs ")
			.append(" 	WHERE daxoa = 0 ");
		if (!khoa.equals("")) {
			sql.append(" 	AND key ilike '%"+khoa+"%' ");
		}
		if (!tenChucNang.equals("")) {
			sql.append(" 	AND name ilike '%"+tenChucNang+"%' ");
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<NhomNguoiDung> getAllNhomUser(String tenNhom){
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT * FROM qtht_nhomusers ")
			.append(" 	WHERE daxoa = 0 ");
	
		if (!tenNhom.equals("")) {
			sql.append(" 	AND ten ilike '%"+tenNhom+"%' ");
		}

		List<NhomNguoiDung> results =  _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<NhomNguoiDung>(NhomNguoiDung.class));
		return results;
	}
	
	public int getAllNhomUserCount(String tenNhom) {
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT COUNT(*) FROM qtht_nhomusers ")
			.append(" 	WHERE daxoa = 0 ");
	
		if (!tenNhom.equals("")) {
			sql.append(" 	AND ten ilike '%"+tenNhom+"%' ");
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public void insert(String tableName, List<String> tableCol, JSONArray tableValue) {
		StringBuilder sqlInsert = new StringBuilder();
		sqlInsert.append(" INSERT INTO " + tableName + " ( " + tableCol.get(0));
		for (int i = 1; i < tableCol.size(); i++) {
			sqlInsert.append(" , " + tableCol.get(i));
		}
		sqlInsert.append(" ) VALUES ");
		for (int i = 0; i < tableValue.length(); i++) {
			JSONObject json = tableValue.getJSONObject(i);
			String query = "( " + json.getLong(tableCol.get(0));
			for (int j = 1; j < tableCol.size(); j++) {
				query += ", " + json.getLong(tableCol.get(j));
			}
			query += " ),";
			sqlInsert.append(query);
		}
		
		sqlInsert.deleteCharAt(sqlInsert.lastIndexOf(","));
		sqlInsert.append(" ; ");
		_jdbcTemplate.execute(sqlInsert.toString());
	}
	
	public void delete(String tableName,String colName, long colValue) {
		StringBuilder sqlDelete = new StringBuilder();
		sqlDelete.append("DELETE FROM " + tableName + " ")
				.append("	WHERE " + colName +" = " + colValue);
		
		_jdbcTemplate.execute(sqlDelete.toString());
	}
	
	public List<Long> getAllChucNangByNhomId(long nhomId){
		String sql = " SELECT chucnangid FROM qtht_nhom_chucnang where nhomid= " + nhomId;
		return _jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<KhoaHoc> getAllKhoaHoc(String tenKhoa, int thangStart,int namStart,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_khoahocs ")
			.append(" WHERE daxoa = 0 ");
		if (!tenKhoa.equals("")) {
			sql.append(" AND ten ilike '%" + tenKhoa+"%' ");
		}
		
		if (namStart > 0) {
			sql.append(" AND nambatdau >= "+ namStart);
		}
		 
		if (thangStart > 0) {
			sql.append(" AND thangbatdau >= "+ thangStart);
		}
		
		sql.append(" ORDER BY nambatdau desc, thangbatdau desc ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<KhoaHoc> lsKhoaHoc = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<KhoaHoc>(KhoaHoc.class));
		return lsKhoaHoc;
		
	}
	
	public int getAllKhoaHocCount(String tenKhoa, int thangStart,int namStart){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qlsv_khoahocs ")
			.append(" WHERE daxoa = 0 ");
		if (!tenKhoa.equals("")) {
			sql.append(" AND ten ilike '%" + tenKhoa+"%' ");
		}
		
		if (namStart > 0) {
			sql.append(" AND nambatdau >= "+ namStart);
		}
		 
		if (thangStart > 0) {
			sql.append(" AND thangbatdau >= "+ thangStart);
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		
	}
	
	public List<LopHocDTO> getAllLopHocDTO(String tenLop, long khoaHocId,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT lh.id,lh.ten,lh.khoahocid FROM qlsv_lophocs lh ")
			.append(" INNER JOIN qlsv_khoahocs kh on kh.id = lh.khoahocid ")
			.append(" WHERE lh.daxoa = 0 ");
		if (!tenLop.equals("")) {
			sql.append(" AND lh.ten ilike '%" + tenLop+"%' ");
		}
		
		if (khoaHocId > 0) {
			sql.append(" AND lh.khoahocid = "+ khoaHocId);
		}
		 
		sql.append(" ORDER BY lh.ten ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<LopHocDTO> lsLopHoc = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<LopHocDTO>(LopHocDTO.class));
		return lsLopHoc;
		
	}
	
	public int getAllLopHocCount(String tenLop, long khoaHocId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qlsv_lophocs lh ")
			.append(" INNER JOIN qlsv_khoahocs kh on kh.id = lh.khoahocid ")
			.append(" WHERE lh.daxoa = 0 ");
		if (!tenLop.equals("")) {
			sql.append(" AND lh.ten ilike '%" + tenLop+"%' ");
		}
		
		if (khoaHocId > 0) {
			sql.append(" AND lh.khoahocid = "+ khoaHocId);
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		
	}
	
	public List<LopHoc> getAllLopHoc(String tenLop, long khoaHocId,int offset,int limit){
		List<LopHoc> lsLopHoc = new ArrayList<>();
		List<LopHocDTO> lsDto = getAllLopHocDTO(tenLop, khoaHocId, offset, limit);
		if (lsDto != null && lsDto.size() > 0) {
			for (LopHocDTO lopHocDTO : lsDto) {
				LopHoc lop = new LopHoc();
				lop.setId(lopHocDTO.getId());
				lop.setTen(lopHocDTO.getTen());
				
				KhoaHoc khoa = this.khoaHocRepository.findById(lopHocDTO.getKhoahocid()).get();
				if (khoa != null) {
					lop.setKhoaHoc(khoa);
				}
				lsLopHoc.add(lop);
			}
		}
		
		return lsLopHoc;
	}
	
	public List<PhongHocDTO> getAllPhongHocDTO(String ten, long coSoId,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id,a.ten,a.cosoid FROM qlsv_phonghocs a ")
			.append(" INNER JOIN qlsv_cosos b on b.id = a.cosoid ")
			.append(" WHERE a.daxoa = 0 ");
		if (!ten.equals("")) {
			sql.append(" AND a.ten ilike '%" + ten+"%' ");
		}
		
		if (coSoId > 0) {
			sql.append(" AND a.cosoid = "+ coSoId);
		}
		 
		sql.append(" ORDER BY a.ten ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<PhongHocDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<PhongHocDTO>(PhongHocDTO.class));
		return results;
		
	}
	
	public int getAllPhongHocCount(String ten, long coSoId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qlsv_phonghocs a ")
			.append(" INNER JOIN qlsv_cosos b on b.id = a.cosoid ")
			.append(" WHERE a.daxoa = 0 ");
		if (!ten.equals("")) {
			sql.append(" AND a.ten ilike '%" + ten+"%' ");
		}
		
		if (coSoId > 0) {
			sql.append(" AND a.cosoid = "+ coSoId);
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		
	}
	
	public List<PhongHoc> getAllPhongHoc(String ten, long coSoId,int offset,int limit){
		List<PhongHoc> lsPhong = new ArrayList<>();
		List<PhongHocDTO> lsDto = getAllPhongHocDTO(ten, coSoId, offset, limit);
		if (lsDto != null && lsDto.size() > 0) {
			for (PhongHocDTO dto : lsDto) {
				PhongHoc lop = new PhongHoc();
				lop.setId(dto.getId());
				lop.setTen(dto.getTen());
				
				CoSo khoa = this.coSoRepository.findById(dto.getCosoid()).get();
				if (khoa != null) {
					lop.setCoSo(khoa);
				}
				lsPhong.add(lop);
			}
		}
		
		return lsPhong;
	}
	
	public List<UserDTO> getAllUser(long nhomId, String ten , int offset, int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT * FROM ");
		if (nhomId == 1) {
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,sv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_sinhviens sv on sv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND sv.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else if(nhomId == 2){
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,gv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_giangviens gv on gv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND gv.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else if (nhomId == 3){
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,qt.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_quantris qt on qt.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND qt.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else if (nhomId == 4){
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,'admin' as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			sql.append(" ) as baocao ");
		}else{
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,sv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_sinhviens sv on sv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND sv.hoten ilike '%"+ten+"%' ");
			}
			
			sql.append("	UNION ALL ");
			sql.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,gv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_giangviens gv on gv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND gv.hoten ilike '%"+ten+"%' ");
			}
			
			sql.append("	UNION ALL ");
			sql.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,qt.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_quantris qt on qt.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND qt.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}
		
		sql.append("	ORDER BY baocao.ten ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT "+ limit + " OFFSET " + offset);
		}
		List<UserDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UserDTO>(UserDTO.class));
		return results;
	}
	
	public int getAllUserCount(long nhomId, String ten) {
		StringBuilder sql = new StringBuilder();
		sql.append(" 	SELECT COUNT(*) FROM ");
		if (nhomId == 1) {
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,sv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_sinhviens sv on sv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND sv.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else if(nhomId == 2){
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,gv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_giangviens gv on gv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND gv.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else if (nhomId == 3){
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,qt.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_quantris qt on qt.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 AND u.nhomid = "+ nhomId);
			if (!ten.equals("")) {
				sql.append("	AND qt.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}else{
			sql.append(" ( ")
				.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,sv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_sinhviens sv on sv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND sv.hoten ilike '%"+ten+"%' ");
			}
			
			sql.append("	UNION ALL ");
			sql.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,gv.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_giangviens gv on gv.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND gv.hoten ilike '%"+ten+"%' ");
			}
			
			sql.append("	UNION ALL ");
			sql.append("	SELECT u.id as id ")
				.append("			,n.ten as tennhom ")
				.append("			,u.nhomid as nhomid ")
				.append("			,qt.hoten as ten ")
				.append("	FROM qtht_users u ")
				.append("	INNER JOIN qtht_nhomusers n on n.id = u.nhomid ")
				.append("	INNER JOIN qlsv_quantris qt on qt.id = u.id ")
				.append("	WHERE u.daxoa = 0 AND n.daxoa = 0 ");
			if (!ten.equals("")) {
				sql.append("	AND qt.hoten ilike '%"+ten+"%' ");
			}
			sql.append(" ) as baocao ");
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<Long> getAllChucNangByUserId(long userId){
		String sql = " SELECT chucnangid FROM qtht_user_chucnang where userid= " + userId;
		return _jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<MonHocDTO> getAllMonHocDTO(String ten, int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.id,a.ten,a.sotiet,a.sobuoi,b.id as giangvienid,b.hoten as tengiangvien FROM qlsv_monhocs a ")
			.append(" INNER JOIN qlsv_giangviens b on b.id = a.giangvienid ")
			.append(" WHERE a.daxoa = 0 ");
		if (!ten.equals("")) {
			sql.append(" AND a.ten ilike '%" + ten+"%' ");
		}
		 
		sql.append(" ORDER BY a.ten ");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<MonHocDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<MonHocDTO>(MonHocDTO.class));
		return results;
		
	}
	
	public int getAllMonHocCount(String ten){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qlsv_monhocs a ")
			.append(" INNER JOIN qlsv_giangviens b on b.id = a.giangvienid ")
			.append(" WHERE a.daxoa = 0 ");
		if (!ten.equals("")) {
			sql.append(" AND a.ten ilike '%" + ten+"%' ");
		}
		
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		
	}
	
	public List<GiangVien> getAllGiangVien(){
		String sql = " SELECT * FROM qlsv_giangviens where daxoa= 0 ORDER BY hoten ";
		List<GiangVien> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<GiangVien>(GiangVien.class));
		return results;
	}
	
	public List<Object[]> getMonHocByLopId(long lopId,int trangThai){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT m.id as id ,m.ten as ten , ml.trangthai as trangthai, ml.id as mlid, ml.hocky FROM qlsv_monhocs m ")
			.append(" INNER JOIN qlsv_lophoc_monhoc ml on m.id = ml.monid ")
			.append(" WHERE m.daxoa = 0 ")
			.append("	AND ml.lopid = " + lopId + " ");
			if(trangThai > 0) {
				sql.append(" AND ml.trangthai != " + trangThai + " ");
			}
			sql.append("	ORDER BY ml.hocky desc, ml.trangthai ");
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[5];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = map.get("ten").toString();
			objects[2] = map.get("trangthai").toString();
			objects[3] = Long.parseLong(map.get("mlid").toString());
			objects[4] = Integer.parseInt(map.get("hocky").toString());
			results.add(objects);
		}
		return results;
	}
	
	public List<Long> getGiangVienByLopId(long lopId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT distinct m.giangvienid FROM qlsv_monhocs m ")
		.append(" INNER JOIN qlsv_lophoc_monhoc ml on m.id = ml.monid ")
		.append(" WHERE m.daxoa = 0 ")
		.append("	AND ml.lopid = " + lopId + " ")
		.append(" AND ml.trangthai != 2 ");
		
		return _jdbcTemplate.queryForList(sql.toString(), Long.class);
	}
	
	public int getMonHoc(long lopId, long monId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qlsv_monhocs m ")
		.append(" INNER JOIN qlsv_lophoc_monhoc ml on m.id = ml.monid ")
		.append(" WHERE m.daxoa = 0 ")
		.append("	AND ml.lopid = " + lopId + " ")
		.append("	AND ml.monid = " + monId + " ")
		.append("	AND ml.trangthai in (0,1,2) ");
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public void deleteMon(long lopId, long monId) {
		StringBuilder sqlDelete = new StringBuilder();
		sqlDelete.append("DELETE FROM qlsv_lophoc_monhoc ")
				.append("	WHERE lopId = " + lopId + " AND monid = " + monId);
		
		_jdbcTemplate.execute(sqlDelete.toString());
	}
	
	public List<ThoiKhoaBieuDTO> getAllThoiKhoaBieu(long lopId,String tuNgay, String denNgay){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tkb.id as id, tkb.cahoc as cahoc, tkb.ngay as ngay, m.id as monid, m.ten as tenmon ")
			.append("		,l.id as lopid, l.ten as tenlop,CASE WHEN tkb.phongid is null THEN 0 ELSE tkb.phongid END AS phongid ")
			.append("		, CASE WHEN tkb.phongid is null THEN '' ELSE p.ten END AS tenphong, tkb.mota as mota ")
			.append(" FROM qlsv_thoikhoabieus tkb ")
			.append(" INNER JOIN qlsv_monhocs m on m.id = tkb.monid ")
			.append(" INNER JOIN qlsv_lophocs l on l.id = tkb.lopid ")
			.append(" LEFT JOIN qlsv_phonghocs p on p.id = tkb.phongid ")
			.append(" WHERE tkb.lopid = "+ lopId + " ");
		if (!tuNgay.isEmpty()) {
			sql.append(" AND tkb.ngay >= CAST('" + tuNgay+"' as TIMESTAMP) ");
		}
		if (!denNgay.isEmpty()) {
			sql.append(" AND tkb.ngay <= CAST('" + denNgay+"' as TIMESTAMP) ");
		}
		sql.append(" ORDER BY tkb.ngay ");
		
		List<ThoiKhoaBieuDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ThoiKhoaBieuDTO>(ThoiKhoaBieuDTO.class));
		return results;
	}
	
	public LopHocDTO getLopHocByUserId(long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT lh.id as id,lh.ten as ten,lh.khoahocid as khoahocid FROM qlsv_lophocs lh ")
			.append(" INNER JOIN qlsv_sinhviens sv ON sv.lopid = lh.id ")
			.append(" WHERE sv.id = " + userId);
		
		List<LopHocDTO> lsLopHoc = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<LopHocDTO>(LopHocDTO.class));
		if (lsLopHoc != null && lsLopHoc.size() > 0) {
			return lsLopHoc.get(0);
		}
		return null;
	}
	
	public List<PhepDTO> getAllPhepByUserId(long userId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,tungay,denngay,songaynghi,lydo,trangthai FROM qlsv_pheps ")
			.append(" WHERE daxoa = 0 ")
			.append("	AND userid = " + userId)
			.append(" ORDER BY trangthai,tungay desc ");
		
		List<PhepDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<PhepDTO>(PhepDTO.class));
		return results;
	}
	
	public int getChucNangByUrlAndUser(String url, long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qtht_chucnangs cn ")
			.append(" INNER JOIN qtht_user_chucnang qtcn ON cn.id = qtcn.chucnangid ")
			.append(" WHERE cn.daxoa = 0 ")
			.append(" 	AND cn.url ilike '%"+url+"%' ")
			.append("	AND qtcn.userid = "+ userId);
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<ThongBaoDTO> getThongBaoByUserId(long userId, int status,String title,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tb.id,tb.tieude,tb.noidung,utb.trangthai,tb.loai,tb.nguoiguiid FROM qtht_thongbaos tb ")
			.append(" INNER JOIN qtht_user_thongbao utb ON utb.thongbaoid = tb.id ")
			.append(" WHERE utb.nguoinhanid = " + userId + " ");
		if (status >=0) {
			sql.append(" AND utb.trangthai = " + status + " ");
		}
		if (!title.equals("")) {
			sql.append(" AND (tb.tieude ilike '%" + title + "%' OR tb.noidung ilike '%"+title+"%' )");
		}
		sql.append(" ORDER BY tb.ngaytao desc");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<ThongBaoDTO> result = _jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<ThongBaoDTO>(ThongBaoDTO.class));
		return result;
	}
	
	public int getCountThongBaoByUserId(long userId, int status,String title) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qtht_thongbaos tb ")
			.append(" INNER JOIN qtht_user_thongbao utb ON utb.thongbaoid = tb.id ")
			.append(" WHERE utb.nguoinhanid = " + userId + " ");
		if (status >=0) {
			sql.append(" AND utb.trangthai = " + status + " ");
		}
		if (!title.equals("")) {
			sql.append(" AND (tb.tieude ilike '%" + title + "%' OR tb.noidung ilike '%"+title+"%' )");
		}
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<ThongBaoDTO> getAllThongBao(long userId, String title,int offset,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tb.id,tb.tieude,tb.noidung,0 as trangthai ,tb.loai,tb.nguoiguiid FROM qtht_thongbaos tb ")
			.append(" WHERE tb.nguoiguiid = " + userId + " ");
		if (!title.equals("")) {
			sql.append(" AND (tb.tieude ilike '%" + title + "%' OR tb.noidung ilike '%"+title+"%' )");
		}
		sql.append(" ORDER BY tb.ngaytao desc");
		if (limit != -1 && offset != -1) {
			sql.append(" LIMIT " + limit + " OFFSET " + offset);
		}
		
		List<ThongBaoDTO> result = _jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<ThongBaoDTO>(ThongBaoDTO.class));
		return result;
	}
	
	public int getCountThongBao(long userId, String title) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qtht_thongbaos tb ")
			.append(" WHERE tb.nguoiguiid = " + userId + " ");
		if (!title.equals("")) {
			sql.append(" AND (tb.tieude ilike '%" + title + "%' OR tb.noidung ilike '%"+title+"%' )");
		}
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<Long> getAllUserIdByLopId(long lopId){
		String sql = " SELECT u.id FROM qtht_users u INNER JOIN qlsv_sinhviens sv ON u.id = sv.id where sv.lopid= " + lopId;
		return _jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<Long> getAllUserIdByKhoaId(long khoaId){
		String sql = " SELECT u.id FROM qtht_users u INNER JOIN qlsv_sinhviens sv ON u.id = sv.id INNER JOIN qlsv_lophocs l ON l.id = sv.lopid where l.khoahocid= " + khoaId;
		return _jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<Long> getAllUserIdByThongBaoId(long tbId){
		String sql = " SELECT nguoinhanid FROM qtht_user_thongbao WHERE thongbaoid = " + tbId;
		return _jdbcTemplate.queryForList(sql,Long.class);
	}
	
	public void upDateTrangThaiThongBao(long thongBaoId, long userId) {
		String sql = "UPDATE qtht_user_thongbao SET trangthai = 1 WHERE thongbaoid = " + thongBaoId + " AND nguoinhanid = " + userId;
		_jdbcTemplate.execute(sql);
	}
	
	public List<Menu> getMenuByUserId(long userId, int module){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT cn.id,cn.name as ten, cn.url, cn.icon FROM qtht_chucnangs cn ")
			.append(" INNER JOIN qtht_user_chucnang ucn ON ucn.chucnangid = cn.id ")
			.append(" WHERE ucn.userid = " + userId + " ")
			.append("		AND cn.module = " + module + " ")
			.append("		AND cn.ismenu = 1 ")
			.append(" ORDER BY cn.name ");
		List<Menu> results = _jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<Menu>(Menu.class));
		return results;
	}
	
	public int checkQuyenByKhoa(long userId, String khoa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM qtht_chucnangs cn ")
			.append(" INNER JOIN qtht_user_chucnang ucn ON ucn.chucnangid = cn.id ")
			.append(" WHERE ucn.userid = " + userId + " ")
			.append("		AND cn.key ilike '%"+khoa+"%' ");
		return _jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}
	
	public List<Object[]> getAllLopByGiangVienId(long giangVienID){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT l.id,l.ten FROM qlsv_lophocs l ")
			.append(" INNER JOIN qlsv_lophoc_monhoc lm ON lm.lopid = l.id ")
			.append(" INNER JOIN qlsv_monhocs m ON m.id = lm.monid ")
			.append(" WHERE m.daxoa = 0 ")
			.append("	AND l.daxoa = 0 ")
			.append("	AND m.giangvienid = " + giangVienID + " ");
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[2];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = map.get("ten").toString();
			results.add(objects);
		}
		return results;
	}
	
	public List<Object[]> getLichDayByLopIdAndMonId(long giangVienID,long lopId,long monId){
		String now = CommonUtil.toString(new Date(), "yyyy-MM-dd");
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tkb.id,tkb.cahoc,tkb.ngay FROM qlsv_thoikhoabieus tkb ")
			.append(" INNER JOIN qlsv_monhocs m ON m.id = tkb.monid ")
			.append(" WHERE m.daxoa = 0 ")
			.append("	AND tkb.lopid = " +lopId + " ")
			.append("	AND tkb.monid = " + monId + " ")
			.append("	AND tkb.ngay <= CAST('" + now+"' as TIMESTAMP) ");
		if (giangVienID > 0) {
			sql.append("	AND m.giangvienid = " + giangVienID + " ");
		}
		sql.append(" ORDER BY tkb.ngay desc ");
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[3];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = Integer.parseInt(map.get("cahoc").toString());
			objects[2] = CommonUtil.todate(map.get("ngay").toString(), "yyyy-MM-dd");
			results.add(objects);
		}
		return results;
	}
	
	public List<Object[]> getAllSinhVienByLopId(long lopId){
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT u.id as id ")
		.append("			,sv.hoten as ten ")
		.append("	FROM qtht_users u ")
		.append("	INNER JOIN qlsv_sinhviens sv on sv.id = u.id ")
		.append("	WHERE u.daxoa = 0 ")
		.append("		AND sv.lopid = " + lopId + " ")
		.append("	ORDER BY sv.hoten ");
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[2];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = map.get("ten").toString();
			results.add(objects);
		}
		return results;
	}
	
	public List<Object[]> getAllMonHocByLopAndGiangVien(long giangVienId, long lopId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT m.id, m.ten FROM qlsv_monhocs m ")
			.append(" INNER JOIN qlsv_lophoc_monhoc lm ON lm.monid = m.id ")
			.append(" WHERE m.daxoa = 0 ")
			.append("	AND m.giangvienid = " + giangVienId + " ")
			.append("	AND lm.lopid = " + lopId)
			.append(" ORDER BY lm.trangthai, m.ten ");
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[2];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = map.get("ten").toString();
			results.add(objects);
		}
		return results;
	}
	
	public List<DiemDanhDTO> getDiemDanhByTkbId(long tkbId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT dd.id,dd.denlop,dd.ghichu,dd.kienthuc,dd.thuchanh,dd.sinhvienid,dd.tkbid FROM qlsv_diemdanhs dd ")
			.append(" WHERE dd.tkbid = " + tkbId);
		List<DiemDanhDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<DiemDanhDTO>(DiemDanhDTO.class));
		return results;
	}
	
	public long getLopMonId(long lopId, long monId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id FROM qlsv_lophoc_monhoc ")
		.append("	WHERE lopid = " + lopId + " ")
		.append("	AND monid = " + monId + " ");
		return _jdbcTemplate.queryForObject(sql.toString(), Long.class);
	}
	
	public List<DiemThi> getDiemThiSV(long lopMonId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_diemthis ")
			.append(" WHERE lopmonid = " + lopMonId);
		
		List<DiemThi> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<DiemThi>(DiemThi.class));
		return results;
	}
	
	public void updateTrangThaiMonHoc(long lopMonId,int trangThai) {
		String sql = "UPDATE qlsv_lophoc_monhoc SET trangthai = "+trangThai+" WHERE id = " + lopMonId;
		_jdbcTemplate.execute(sql);
	}
	
	public List<ThoiKhoaBieuDTO> getAllThoiKhoaBieuByGiangVienId(long giangVienId,long lopId,String tuNgay, String denNgay){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tkb.id as id, tkb.cahoc as cahoc, tkb.ngay as ngay, m.id as monid, m.ten as tenmon ")
			.append("		,l.id as lopid, l.ten as tenlop,CASE WHEN tkb.phongid is null THEN 0 ELSE tkb.phongid END AS phongid ")
			.append("		, CASE WHEN tkb.phongid is null THEN '' ELSE p.ten END AS tenphong, tkb.mota as mota ")
			.append(" FROM qlsv_thoikhoabieus tkb ")
			.append(" INNER JOIN qlsv_monhocs m on m.id = tkb.monid ")
			.append(" INNER JOIN qlsv_lophocs l on l.id = tkb.lopid ")
			.append(" LEFT JOIN qlsv_phonghocs p on p.id = tkb.phongid ")
			.append(" WHERE m.daxoa= 0 ");
		if(giangVienId > 0) {
			sql.append("	AND m.giangvienid = " + giangVienId+" ");
		}
		if (!tuNgay.isEmpty()) {
			sql.append(" AND tkb.ngay >= CAST('" + tuNgay+"' as TIMESTAMP) ");
		}
		if (!denNgay.isEmpty()) {
			sql.append(" AND tkb.ngay <= CAST('" + denNgay+"' as TIMESTAMP) ");
		}
		if(lopId > 0) {
			sql.append(" AND tkb.lopid = " + lopId);
			sql.append(" ORDER BY tkb.ngay desc");
		}else {
			sql.append(" ORDER BY tkb.ngay ");
		}
		
		List<ThoiKhoaBieuDTO> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ThoiKhoaBieuDTO>(ThoiKhoaBieuDTO.class));
		return results;
	}
	
	public List<Object[]> getAllPhepSV(long giangVienId,String tuNgay){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT distinct p.id,p.tungay,p.denngay,p.songaynghi,p.lydo,p.trangthai,sv.hoten,l.ten FROM qlsv_pheps p ")
			.append("	INNER JOIN qlsv_sinhviens sv ON sv.id = p.userid ")
			.append("	INNER JOIN qlsv_lophocs l ON sv.lopid = l.id ")
			.append("	INNER JOIN qlsv_lophoc_monhoc lm ON l.id = lm.lopid ")
			.append("	INNER JOIN qlsv_monhocs m ON m.id = lm.monid ")
			.append(" WHERE m.daxoa = 0 ");
		if(giangVienId > 0) {
			sql.append("	AND m.giangvienid = " + giangVienId + " ");
		}
		if (!tuNgay.equals("")) {
			sql.append("	AND p.denngay >= CAST('" + tuNgay+"' as TIMESTAMP) ");
		}
		sql.append(" ORDER BY trangthai,tungay desc ");
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[8];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = CommonUtil.todate(map.get("tungay").toString(), "yyyy-MM-dd");
			objects[2] = CommonUtil.todate(map.get("denngay").toString(), "yyyy-MM-dd");
			objects[3] = map.get("songaynghi").toString();
			objects[4] = map.get("lydo").toString();
			objects[5] = Integer.parseInt(map.get("trangthai").toString());
			objects[6] = map.get("hoten").toString();
			objects[7] = map.get("ten").toString();
			results.add(objects);
		}
		return results;
	}
	
	public List<WorkTaskDetail> getWorkTaskDetailByWTId(long workTaskId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_worktask_detail ")
			.append(" WHERE daxoa = 0 ")
			.append(" 	AND worktaskid = " + workTaskId + " ")
			.append(" ORDER BY thutu ");
		
		List<WorkTaskDetail> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<WorkTaskDetail>(WorkTaskDetail.class));
		return results;
	}
	
	public List<WorkTask> getAllWorkTask(long monId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_worktasks ")
			.append(" WHERE daxoa = 0 ")
			.append("	AND monid = " + monId + " ")
			.append(" ORDER BY thutu ");
		
		List<WorkTask> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<WorkTask>(WorkTask.class));
		return results;
	}
	
	public int getMaxThuTuOfWT(long monId) {
		String sql = "SELECT case when max(thutu) is null then 0 else max(thutu) end FROM qlsv_worktasks WHERE daxoa = 0 AND monid = " + monId;
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public List<DanhGia> getAllDanhGia(long monId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_danhgias ")
			.append(" WHERE daxoa = 0 ")
			.append("	AND monid = " + monId + " ")
			.append(" ORDER BY thutu ");
		
		List<DanhGia> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<DanhGia>(DanhGia.class));
		return results;
	}
	
	public int getMaxThuTuOfDanhGia(long monId) {
		String sql = "SELECT case when max(thutu) is null then 0 else max(thutu) end FROM qlsv_danhgias WHERE daxoa = 0 AND monid = " + monId;
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public void updateWorkTask(long lopId, long monId) {
		String sql = "UPDATE qlsv_lophoc_monhoc SET isworktask = 1 WHERE lopid = " + lopId + " AND monid = " + monId;
		_jdbcTemplate.execute(sql);
	}
	
	public void updateDanhGia(long lopId, long monId) {
		String sql = "UPDATE qlsv_lophoc_monhoc SET isdanhgia = 1 WHERE lopid = " + lopId + " AND monid = " + monId;
		_jdbcTemplate.execute(sql);
	}
	
	public int select(String colum, long lopId, long monId) {
		String sql = "SELECT "+colum+" FROM qlsv_lophoc_monhoc WHERE lopid = " + lopId + " AND monid = " + monId;
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public int countSVWT(long sinhVienId, long wtId) {
		String sql = "SELECT COUNT(*) FROM qlsv_sinhvien_worktask WHERE sinhvienid = " + sinhVienId + " AND worktaskdetailid = " + wtId;
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public void updateSinhVienWorkTask(SinhVienWorkTask sv) {
		int count = countSVWT(sv.getSinhVienId(), sv.getWorkTaskDetailId());
		String sql = "";
		if(count > 0) {
			//update
			sql = " UPDATE qlsv_sinhvien_worktask SET ngaytao = '" + sv.getNgayTao()+"' "
				+ ", ykien = '" + sv.getYKien() + "', ketqua = "+ sv.getKetQua()
				+" WHERE sinhvienid = " + sv.getSinhVienId() + " AND worktaskdetailid = " + sv.getWorkTaskDetailId() + " ; ";
		}else {
			//add new
			sql = " INSERT INTO qlsv_sinhvien_worktask (ngaytao,sinhvienid,worktaskdetailid,ykien,ketqua ) "
					+ " VALUES ('"+sv.getNgayTao()+"', "+sv.getSinhVienId()+", "+sv.getWorkTaskDetailId()+", '" + sv.getYKien()+"', " + sv.getKetQua()+" ); ";
		}
		_jdbcTemplate.execute(sql);
	}
	
	public List<SinhVienWorkTask> getSinhVienWTBySinhVienId(long sinhVienId,long monId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT svwt.id, svwt.ngaytao, svwt.sinhvienid, svwt.worktaskdetailid, svwt.ykien,svwt.ketqua FROM qlsv_sinhvien_worktask svwt ")
			.append(" INNER JOIN qlsv_worktask_detail wtd ON wtd.id = svwt.worktaskdetailid ")
			.append(" INNER JOIN qlsv_worktasks wt ON wt.id = wtd.worktaskid ")
			.append(" WHERE svwt.sinhvienid = " + sinhVienId)
			.append("	AND wt.monid = " + monId)
			.append(" ORDER BY svwt.worktaskdetailid ");
		
		List<SinhVienWorkTask> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<SinhVienWorkTask>(SinhVienWorkTask.class));
		return results;
	}
	
	public int countSVDG(long sinhVienId, long wtId,long danhGiaId) {
		String sql = "SELECT COUNT(*) FROM qlsv_sinhvien_danhgia WHERE sinhvienid = " + sinhVienId + " AND worktaskid = " + wtId + " AND danhgiaid = " + danhGiaId;
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public void updateSinhVienDanhGia(SinhVienDanhGia sv) {
		int count = countSVDG(sv.getSinhVienId(), sv.getWorkTaskId(), sv.getDanhGiaId());
		String sql = "";
		if(count > 0) {
			//update
			sql = " UPDATE qlsv_sinhvien_danhgia SET ngaytao = '" + sv.getNgayTao()+"' "
				+ ", traloi = '" + sv.getTraLoi() + "', ketqua = "+ sv.getKetQua()
				+" WHERE sinhvienid = " + sv.getSinhVienId() + " AND worktaskid = " + sv.getWorkTaskId() + " AND danhgiaid = "+sv.getDanhGiaId()+" ; ";
		}else {
			//add new
			sql = " INSERT INTO qlsv_sinhvien_danhgia (ngaytao,sinhvienid,worktaskid,traloi,ketqua,danhgiaid ) "
					+ " VALUES ('"+sv.getNgayTao()+"', "+sv.getSinhVienId()+", "+sv.getWorkTaskId()+", '" + sv.getTraLoi()+"', " + sv.getKetQua()+", "+sv.getDanhGiaId()+" ); ";
		}
		_jdbcTemplate.execute(sql);
	}
	
	public List<SinhVienDanhGia> getSinhVienDanhGiaWTBySinhVienId(long sinhVienId,long monId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT svwt.id, svwt.ngaytao, svwt.sinhvienid, svwt.worktaskid, svwt.traloi,svwt.ketqua,svwt.danhgiaid FROM qlsv_sinhvien_danhgia svwt ")
			.append(" INNER JOIN qlsv_worktasks wt ON wt.id = svwt.worktaskid ")
			.append(" WHERE wt.daxoa = 0 AND svwt.sinhvienid = " + sinhVienId)
			.append("	AND wt.monid = " + monId)
			.append(" ORDER BY svwt.worktaskid ");
		
		List<SinhVienDanhGia> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<SinhVienDanhGia>(SinhVienDanhGia.class));
		return results;
	}
	
	public List<SinhVienDanhGia> getSinhVienDanhGiaBySinhVienId(long sinhVienId,long monId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT svwt.id, svwt.ngaytao, svwt.sinhvienid, svwt.worktaskid, svwt.traloi,svwt.ketqua,svwt.danhgiaid FROM qlsv_sinhvien_danhgia svwt ")
			.append(" INNER JOIN qlsv_danhgias wt ON wt.id = svwt.danhgiaid ")
			.append(" WHERE wt.daxoa = 0 AND svwt.sinhvienid = " + sinhVienId)
			.append("	AND wt.monid = " + monId)
			.append(" ORDER BY svwt.danhgiaid ");
		
		List<SinhVienDanhGia> results = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<SinhVienDanhGia>(SinhVienDanhGia.class));
		return results;
	}
	
	public NhatKy getNhatKyByTkbId(long tkbId) {
		String sql = "SELECT * FROM qlsv_nhatkys WHERE daxoa = 0 AND tkbid = " + tkbId;
		List<NhatKy> lsNhatKy = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<NhatKy>(NhatKy.class));
		if (lsNhatKy != null && lsNhatKy.size() > 0) {
			return lsNhatKy.get(0);
		}
		return null;
	}
	
	public PhongHocDTO getPhongHocById(long phongId) {
		String sql = "SELECT id,ten,cosoid FROM qlsv_phonghocs WHERE daxoa = 0 AND id = " + phongId;
		List<PhongHocDTO> lsPhong = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<PhongHocDTO>(PhongHocDTO.class));
		if (lsPhong != null && lsPhong.size() > 0) {
			return lsPhong.get(0);
		}
		return null;
	}
	
	public int getBuoiThuInNhatKy(ThoiKhoaBieu tkb) {
		String sql = "SELECT case when max(nk.buoithu) is null then 0 else max(nk.buoithu) end FROM qlsv_nhatkys nk "
				+ " INNER JOIN qlsv_thoikhoabieus tkb ON tkb.id = nk.tkbid "
				+ " WHERE nk.daxoa = 0 "
				+ " 	AND tkb.monid = " + tkb.getMon().getId()
				+ " 	AND tkb.lopid = " + tkb.getLop().getId();
		return _jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public UniFileUpLoads getUniFileUploadByMaSo(String maSo) {
		String sql = " SELECT * FROM qtht_filedinhkem WHERE maso = '"+maSo +"' ";
		List<UniFileUpLoads> lsFile = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UniFileUpLoads>(UniFileUpLoads.class));
		if (lsFile != null && lsFile.size() > 0) {
			return lsFile.get(0);
		}
		return null;
	}
	
	public List<UniFileUpLoads> getUniFileUpLoads(long doiTuongId, long kieu){
		String sql = " SELECT * FROM qtht_filedinhkem WHERE doituongid = "+doiTuongId +" AND kieu = "+ kieu;
		List<UniFileUpLoads> lsFile = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UniFileUpLoads>(UniFileUpLoads.class));
		return lsFile;
	}
	
	public List<Object[]> getDataWTByLopId(long lopId,long monId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT sv.id ")
			.append("	, ( ")
			.append("		SELECT COUNT(*) FROM qlsv_sinhvien_worktask svwt ")
			.append("			LEFT JOIN qlsv_worktask_detail wtd ON wtd.id = svwt.worktaskdetailid ")
			.append("			LEFT JOIN qlsv_worktasks wt ON wt.id = wtd.worktaskid ")
			.append("		WHERE svwt.ketqua = 1  ")
			.append("			AND svwt.sinhvienid = sv.id ") 
			.append("			AND wt.monid = " + monId)
			.append("			AND wtd.daxoa = 0 ")
			.append("	  ) as lamtot ")
			.append("	 , ( ")
			.append("		SELECT COUNT(*) FROM qlsv_sinhvien_worktask svwt ")
			.append("			LEFT JOIN qlsv_worktask_detail wtd ON wtd.id = svwt.worktaskdetailid ")
			.append("			LEFT JOIN qlsv_worktasks wt ON wt.id = wtd.worktaskid ")
			.append("		WHERE svwt.ketqua = 2 ")
			.append("			AND svwt.sinhvienid = sv.id ")
			.append("			AND wt.monid = " + monId)
			.append("		 	AND wtd.daxoa = 0 ")
			.append("	  ) as lamduoc ")
			.append("	 , (  ")
			.append("		SELECT COUNT(*) FROM qlsv_sinhvien_worktask svwt ")
			.append("			LEFT JOIN qlsv_worktask_detail wtd ON wtd.id = svwt.worktaskdetailid ")
			.append("			LEFT JOIN qlsv_worktasks wt ON wt.id = wtd.worktaskid ")
			.append("		WHERE svwt.ketqua = 3  ")
			.append("			AND svwt.sinhvienid = sv.id  ")
			.append("			AND wt.monid = "+monId)
			.append("		 	AND wtd.daxoa = 0 ")
			.append("	  ) as chuaduoc  ")
			.append("FROM qlsv_sinhviens sv  ")
			.append("LEFT JOIN qlsv_lophocs l ON l.id = sv.lopid  ")
			.append("WHERE sv.lopid = "+lopId)
			.append("  AND sv.daxoa = 0 ")
			.append("GROUP BY sv.id  ");
		System.out.println("sql: " + sql.toString());
		
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[4];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = Integer.parseInt(map.get("lamtot").toString());
			objects[2] = Integer.parseInt(map.get("lamduoc").toString());
			objects[3] = Integer.parseInt(map.get("chuaduoc").toString());
			results.add(objects);
		}
		return results;
	}
	
	public NgoaiHeThong getDiemBySinhVienId(long sinhVienId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM qlsv_diemngoaihethongs ")
			.append(" WHERE sinhvienid = " + sinhVienId);
		
		List<NgoaiHeThong> lsLopHoc = _jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<NgoaiHeThong>(NgoaiHeThong.class));
		if (lsLopHoc != null && lsLopHoc.size() > 0) {
			return lsLopHoc.get(0);
		}
		return new NgoaiHeThong();
	}
	
	public List<Object[]> getMonHocByLopAndHocKy(int hocKy, long lopId){
		StringBuilder sql = new StringBuilder();
		sql.append(" select m.id,m.ten,lm.hocky,m.sotiet from qlsv_lophoc_monhoc lm ")
			.append(" INNER JOIN qlsv_monhocs m ON m.id = lm.monid ")
			.append(" where trangthai = 2 ")
			.append(" AND m.daxoa = 0 ")
			.append(" AND lm.hocky = " + hocKy)
			.append(" AND lopid = " + lopId);
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[4];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = map.get("ten").toString();
			objects[2] = Integer.parseInt(map.get("hocky").toString());
			objects[3] = Integer.parseInt(map.get("sotiet").toString());
			results.add(objects);
		}
		return results;
	}
	
	public List<Object[]> getDiemThi(int hocKy, long sinhVienId){
		StringBuilder sql = new StringBuilder();
		sql.append(" select m.id,m.sotiet,d.lythuyet,d.thuchanh FROM qlsv_diemthis d ")
			.append(" INNER JOIN qlsv_lophoc_monhoc lm ON lm.id = d.lopmonid ")
			.append(" INNER JOIN qlsv_monhocs m ON m.id = lm.monid ")
			.append(" where trangthai = 2 ")
			.append(" AND m.daxoa = 0 ")
			.append(" AND lm.hocky = " + hocKy)
			.append(" AND d.sinhvienid = " + sinhVienId);
		List<Object[]> results = new ArrayList<Object[]>();
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(sql.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[4];
			objects[0] = Long.parseLong(map.get("id").toString());
			objects[1] = Integer.parseInt(map.get("sotiet").toString());
			objects[2] = map.get("lythuyet").toString();
			objects[3] = map.get("thuchanh").toString();
			results.add(objects);
		}
		return results;
	}
	
}
