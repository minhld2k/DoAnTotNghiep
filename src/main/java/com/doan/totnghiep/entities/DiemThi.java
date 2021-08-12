package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_diemthis")
@Data
@NoArgsConstructor
public class DiemThi {
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.doan.totnghiep.util.IDGenerator")
	private Long id;
	
	@Column(name = "sinhvienid")
	private Long sinhVienId;
	
	@Column(name = "lythuyet", length = 10)
	private String lyThuyet;
	
	@Column(name = "thuchanh", length = 10)
	private String thucHanh;
	
	@Column(name = "ghichu")
	private String ghiChu;
	
	@Column(name = "lopmonid")
	private Long lopMonId;
	
	@Column(name = "nguoitao",length = 100)
	private String nguoiTao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao")
	private Date ngayTao;
}
