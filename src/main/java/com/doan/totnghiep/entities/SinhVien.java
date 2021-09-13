package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_sinhviens")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SinhVien{
	@Id
	private Long id;
	
	@Column(name = "daxoa")
	private Byte daXoa;
	
	@Column(name = "nguoitao",length = 100)
	private String nguoiTao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao")
	private Date ngayTao;
	
	@Column(name="nguoisua",length = 100)
	private String nguoiSua;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaysua")
	private Date ngaySua;
	
	@Column(name="hoten")
	private String hoTen;
	
	@Column(name="diachi")
	private String diaChi;
	
	@Column(name="gioitinh")
	private Integer gioiTinh;
	
	@Column(name="sodienthoaicanhan")
	private String soDienThoaiCaNhan;
	
	@Column(name="sodienthoaigiadinh")
	private String soDienThoaiGiaDinh;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaysinh")
	private Date ngaySinh;
	
	@Column(name="mota")
	private String moTa;
	
	@Column(name="ma")
	private String ma;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lopid")
	private LopHoc lop;
}
