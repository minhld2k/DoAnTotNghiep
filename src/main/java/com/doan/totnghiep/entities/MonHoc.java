package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_monhocs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonHoc extends Common{
	
	private static final long serialVersionUID = 1L;

	@Column
	private String ten;
	
	@Column(name = "sotiet")
	private int soTiet;
	
	@Column(name = "sobuoi")
	private int soBuoi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "giangvienid")
	private GiangVien giangVien;
	
}
