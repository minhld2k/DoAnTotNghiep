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
@Table(name = "qtht_thongbaos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBao extends Common {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "tieude")
	private String tieuDe;
	
	@Column(name = "noidung")
	private String noiDung;
	
	@Column
	private Integer loai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nguoiguiid")
	private User user;

}
