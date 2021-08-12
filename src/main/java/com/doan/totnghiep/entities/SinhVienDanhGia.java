package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_sinhvien_danhgia")
@Data
@NoArgsConstructor
public class SinhVienDanhGia {
	@Id
    @GeneratedValue(generator = "bigid",strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sinhvienid")
	private Long sinhVienId;
	
	@Column(name = "worktaskid")
	private Long workTaskId;
	
	@Column(name = "ketqua")
	private Integer ketQua;
	
	@Column(name = "danhgiaid")
	private Long danhGiaId;
	
	@Column(name = "traloi", length = 500)
	private String traLoi;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao")
	private Date ngayTao;
}
