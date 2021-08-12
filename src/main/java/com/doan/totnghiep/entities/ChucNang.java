package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qtht_chucnangs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChucNang extends Common{

	private static final long serialVersionUID = 1L;

	@Column
	private String name;
	
	@Column
	private String key;
	
	@Column
	private String url;
	
	@Column
	private String icon;
	
	@Column
	private Integer ismenu;
	
	@Column
	private Integer module;
}
