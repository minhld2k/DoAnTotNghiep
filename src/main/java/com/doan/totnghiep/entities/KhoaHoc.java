package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_khoahocs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KhoaHoc extends Common {
	
	private static final long serialVersionUID = 1L;
	
	@Column
	private String ten;
	
	@Column(name = "thangbatdau")
	private int thangBatDau;
	
	@Column(name = "nambatdau")
	private int namBatDau;
	
	@Column(name = "thangketthuc")
	private int thangKetThuc;
	
	@Column(name = "namketthuc")
	private int namKetThuc;

}
