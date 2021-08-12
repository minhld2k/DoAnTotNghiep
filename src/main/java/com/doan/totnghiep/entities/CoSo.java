package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_cosos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoSo extends Common{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String ten;
	
	@Column(name = "diachi" ,length = 500)
	private String diaChi;
}
