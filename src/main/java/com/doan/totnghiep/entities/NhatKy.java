package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_nhatkys")
@Data
@NoArgsConstructor
public class NhatKy extends Common{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "tkbid")
	private Long tkbId;
	
	@Column(name = "buoithu")
	private Integer buoiThu;
	
	@Column(name = "worktaskid")
	private Long workTaskId;
	
	@Column(name = "loinhanpdt")
	private String loiNhanPdt;
	
	@Column(name = "phongid")
	private Long phongId;
	
	@Column(name = "giovao")
	private String gioVao;
	
	@Column(name = "giobatdau")
	private String gioBatDau;
	
	@Column(name = "danhgiavao")
	private Integer danhGiaVao;
	
	@Column(name = "lydovao")
	private Integer lyDoVao;
	
	@Column(name = "giora")
	private String gioRa;
	
	@Column(name = "danhgiara")
	private Integer danhGiaRa;
	
	@Column(name = "lydora")
	private Integer lyDoRa;
	
	@Column(name = "siso")
	private Integer siSo;
	
	@Column(name = "hieubai")
	private Integer hieuBai;
	
	@Column(name = "kohieu")
	private Integer kohieu;
	
	@Column(name = "danhgiagv")
	private String danhGiaGv;
	
	@Column(name = "loinhangv")
	private String loiNhanGv;
	
	@Column(name = "ghichu")
	private String ghiChu;
}
