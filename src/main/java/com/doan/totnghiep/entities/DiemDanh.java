package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_diemdanhs")
@Data
@NoArgsConstructor
public class DiemDanh extends Common {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "denlop")
	private int denLop;
	
	@Column(name = "kienthuc")
	private int kienThuc;
	
	@Column(name = "thuchanh")
	private int thucHanh;
	
	@Column(name = "ghichu")
	private String ghiChu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tkbid")
	private ThoiKhoaBieu tkb;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sinhvienid")
	private User sinhVien;
}
