package com.doan.totnghiep.entities;

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
@Table(name = "qlsv_phonghocs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhongHoc extends Common{
	
	private static final long serialVersionUID = 1L;
	
	@Column
	private String ten;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cosoid")
	private CoSo coSo;
}
