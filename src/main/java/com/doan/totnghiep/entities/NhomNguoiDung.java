package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qtht_nhomusers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NhomNguoiDung extends Common {
	private static final long serialVersionUID = 1L;

	@Column
	private String ten;
	
}
