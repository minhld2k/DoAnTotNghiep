package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_diemngoaihethongs")
@Data
@NoArgsConstructor
public class NgoaiHeThong {
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.doan.totnghiep.util.IDGenerator")
	private Long id;
	
	@Column(name = "sinhvienid")
	private Long sinhVienId;
	
	@Column(name = "thuchien")
	private String thucHien;
	
	@Column(name = "baocao")
	private String baoCao;
	
	@Column(name = "chinhtri")
	private String chinhTri;
}
