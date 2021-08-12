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

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_sinhvien_worktask")
@Data
@NoArgsConstructor
public class SinhVienWorkTask {
	@Id
    @GeneratedValue(generator = "bigid",strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sinhvienid")
	private Long sinhVienId;
	
	@Column(name = "worktaskdetailid")
	private Long workTaskDetailId;
	
	@Column(name = "ketqua")
	private Integer ketQua;
	
	@Column(name = "ykien", length = 500)
	private String yKien;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao")
	private Date ngayTao;
}
